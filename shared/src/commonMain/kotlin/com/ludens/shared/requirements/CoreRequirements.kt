package com.ludens.shared.requirements

import com.ludens.shared.core.CFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharedFlow
import kotlin.reflect.KClass

expect abstract class PlatformViewModel() {

    protected fun onCleared()

    abstract fun destroy()
}

expect class ViewStateContainer<BaseViewState: Any>() {
    fun contains(type: KClass<BaseViewState>): Boolean
    fun toCFlows(): List<CFlow<BaseViewState>>
    fun put(type: KClass<BaseViewState>, sharedFlow: SharedFlow<BaseViewState>)
}

expect class Resource

expect fun ensureMainThread()

class EnsureMainThreadException : IllegalStateException("Should be called on the main thread!")

// TODO: Not needed when https://github.com/Kotlin/kotlinx.coroutines/issues/2445 gets resolved
expect fun sharedDispatcher(): CoroutineDispatcher
