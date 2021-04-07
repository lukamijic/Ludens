package com.ludens.shared.extensions

import com.ludens.shared.core.SafeCoroutineExceptionHandler
import com.ludens.shared.requirements.logE
import com.ludens.shared.requirements.sharedDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

/**
 * Only proceed with the given action if the coroutine has not been cancelled.
 * Necessary because Flow.collect receives items even after coroutine was cancelled
 * https://github.com/Kotlin/kotlinx.coroutines/issues/1265
 */
suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (T) -> Unit) {
    collect {
        if (coroutineContext.isActive) action(it)
    }
}

/** Factory method for MutableSharedFlow with PublishSubject like behaviour */
fun <T> mutableSharedFlow() = MutableSharedFlow<T>(extraBufferCapacity = 1)

/** Factory method for MutableSharedFlow with BehaviorSubject-without-initial-value like behaviour */
fun <T> mutableSharedFlowWithLatest() = MutableSharedFlow<T>(replay = 1, extraBufferCapacity = 1)

/** Shares the chain and replays the latest emission to new subscribers */
fun <T> Flow<T>.shareReplayLatest() =
    shareIn(CoroutineScope(sharedDispatcher() + SafeCoroutineExceptionHandler { _, throwable ->
        logE("ShareReplayLatest failed", throwable)
    }), SharingStarted.WhileSubscribed(replayExpirationMillis = 0), replay = 1)
