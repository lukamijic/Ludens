package com.ludens.shared.requirements

import android.os.Looper
import androidx.lifecycle.ViewModel
import com.ludens.shared.core.CFlow
import com.ludens.shared.core.wrap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlin.reflect.KClass

actual abstract class PlatformViewModel actual constructor(): ViewModel() {

    protected actual override fun onCleared() {
        destroy()
    }

    actual abstract fun destroy()
}

actual class ViewStateContainer<BaseViewState :  Any> actual  constructor() {

    private val typeToSharedFlow: MutableMap<KClass<BaseViewState>, SharedFlow<BaseViewState>> = mutableMapOf()

    actual fun contains(type: KClass<BaseViewState>) : Boolean = typeToSharedFlow.contains(type)
    actual fun toCFlows(): List<CFlow<BaseViewState>> = typeToSharedFlow.values.map { it.wrap() }
    actual fun put(type: KClass<BaseViewState>, sharedFlow: SharedFlow<BaseViewState>) {
        typeToSharedFlow[type] = sharedFlow
    }
}

actual class Resource(val id: Int)

actual fun ensureMainThread() {
    if (Looper.getMainLooper().thread != Thread.currentThread()) throw EnsureMainThreadException()
}

actual fun sharedDispatcher(): CoroutineDispatcher = Dispatchers.Default
