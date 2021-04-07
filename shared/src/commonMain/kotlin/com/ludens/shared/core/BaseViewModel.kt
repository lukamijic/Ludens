package com.ludens.shared.core

import com.ludens.shared.extensions.mutableSharedFlowWithLatest
import com.ludens.shared.extensions.safeCollect
import com.ludens.shared.navigation.Router
import com.ludens.shared.navigation.RoutingActionsDispatcher
import com.ludens.shared.requirements.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

open class BaseViewModel<BaseViewState : Any>(
    private val routingActionsDispatcher: RoutingActionsDispatcher
) : PlatformViewModel() {

    private val viewStateMap = ViewStateContainer<BaseViewState>()
    private val job = SupervisorJob()

    private val backgroundScope: CoroutineScope = CoroutineScope(Dispatchers.Default + job)

    init {
        logD("Init ${this::class.simpleName}")
    }

    fun viewStates(): Collection<CFlow<BaseViewState>> = viewStateMap.toCFlows()

    inline fun <reified T: BaseViewState> query(noinline flowFactory: () -> Flow<T>) =
        query(T::class as KClass<BaseViewState>, flowFactory as () -> Flow<BaseViewState>)

    fun runCommand(completable: suspend () -> Unit) {
        backgroundScope.launch(SafeCoroutineExceptionHandler { _, throwable ->
            logE("Executing ${this@BaseViewModel::class.simpleName} ${completable::class.simpleName} failed", throwable)
        }) {
            logD("Executing command in ${this@BaseViewModel::class.simpleName} ${completable::class.simpleName}")
            completable()
        }
    }

    fun viewModelBoundJob(job: suspend () -> Unit) {
        backgroundScope.launch(SafeCoroutineExceptionHandler { _, throwable ->
            logE("View model job ${this@BaseViewModel::class.simpleName} failed", throwable)
        }) {
            job()
        }
    }

    /** Invoke to route to another screen */
    protected fun dispatchRoutingAction(routingAction: (Router) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            routingActionsDispatcher.dispatch(routingAction)
        }
    }

    /** Invoke to route to another screen. If another routing action with the same [actionId] is already queued, the old one will be removed. */
    protected fun dispatchDistinctRoutingAction(actionId: String, routingAction: (Router) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            routingActionsDispatcher.dispatchDistinct(actionId, routingAction)
        }
    }

    override fun destroy() {
        logD("Deinit ${this::class.simpleName}")
        job.cancelChildren()
    }

    @Deprecated("Internal usage only! Visible because of inlining")
    fun query(viewStateClass: KClass<BaseViewState>, flowFactory: () -> Flow<BaseViewState>) {
        if (viewStateMap.contains(viewStateClass)) throw IllegalArgumentException("Flow<${viewStateClass.simpleName}> already registered")

        val viewStatePublisher = mutableSharedFlowWithLatest<BaseViewState>()
        viewStateMap.put(viewStateClass, viewStatePublisher)

        backgroundScope.launch(SafeCoroutineExceptionHandler { _, throwable ->
            logE("Query ${this@BaseViewModel::class.simpleName} ${viewStateClass.simpleName} failed", throwable)
        }) {
            flowFactory()
                .onEach { logD("New emission of ${this@BaseViewModel::class.simpleName} ${viewStateClass.simpleName} {${it.toString().take(100)}}") }
                .safeCollect { viewStatePublisher.tryEmit(it) }
        }
    }

    open fun goBack() = dispatchRoutingAction(Router::goBack)
}

inline fun SafeCoroutineExceptionHandler(crossinline handler: (CoroutineContext, Throwable) -> Unit): CoroutineExceptionHandler =
    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, exception: Throwable) {
            if (context.isActive) handler(context, exception)
            else logW("Error occurred but the consumer is no longer active", exception)
        }
    }
