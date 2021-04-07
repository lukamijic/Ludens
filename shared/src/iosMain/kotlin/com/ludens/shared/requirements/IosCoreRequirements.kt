package com.ludens.shared.requirements

import com.ludens.shared.core.CFlow
import com.ludens.shared.core.wrap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze
import kotlin.reflect.KClass

actual abstract class PlatformViewModel actual constructor() {

    protected actual fun onCleared() {
        destroy()
    }

    actual abstract fun destroy()
}

actual class ViewStateContainer<BaseViewState : Any> actual constructor() {

    private val reference: AtomicReference<Node<BaseViewState>?> = AtomicReference(null)

    actual fun contains(type: KClass<BaseViewState>): Boolean {
        var node: Node<BaseViewState>? = reference.value
        while (node != null) {
            if (node.type == type) return true
            node = node.next
        }
        return false
    }

    actual fun toCFlows(): List<CFlow<BaseViewState>> =
        mutableListOf<CFlow<BaseViewState>>().apply {
            var node: Node<BaseViewState>? = reference.value
            while (node != null) {
                add(node.sharedFlow.wrap())
                node = node.next
            }
        }

    actual fun put(type: KClass<BaseViewState>, sharedFlow: SharedFlow<BaseViewState>) {
        reference.value = Node(type, sharedFlow, reference.value).freeze()
    }

    private class Node<BaseViewState : Any>(
        val type: KClass<BaseViewState>,
        val sharedFlow: SharedFlow<BaseViewState>,
        val next: Node<BaseViewState>?
    )
}

actual class Resource(val id: String)

actual fun ensureMainThread() {
    if (!NSThread.currentThread.isMainThread) throw EnsureMainThreadException()
}

actual fun sharedDispatcher(): CoroutineDispatcher = Dispatchers.Main
