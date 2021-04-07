package com.ludens.androidApp.navigation.extensions

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/** Must be called from main thread. */
fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    ensureMainThread { beginTransaction().func().commitNow() }

/** Must be called from main thread. */
fun FragmentManager.inTransactionAndAddToBackStack(name: String? = null, func: FragmentTransaction.() -> FragmentTransaction) =
    ensureMainThread {
        beginTransaction().func().addToBackStack(name).commit()
        executePendingTransactions()
    }

/** Must be called from main thread. */
fun FragmentManager.inTransaction(addToBackStack: Boolean, name: String? = null, func: FragmentTransaction.() -> FragmentTransaction) =
    ensureMainThread {
        beginTransaction().apply {
            func()
            if (addToBackStack) addToBackStack(name)
        }
            .commit()
        executePendingTransactions()
    }

/** Must be called from main thread. */
fun FragmentManager.removeFragmentIfItExists(fragmentTag: String) =
    ensureMainThread {
        findFragmentByTag(fragmentTag)?.let {
            beginTransaction()
                .remove(it)
                .commitNow()
        }
    }

/** Must be called from main thread. */
fun FragmentManager.clearBackStackAndInTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    ensureMainThread {
        popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        beginTransaction()
            .func()
            .commitNow()
    }

/**
 * Clears fragments added to the back stack.
 * When back stack is cleared while the container screen is not visible and the top screen has exit animation, onDestroy is not called.
 * In order to avoid that bug, [FragmentTransaction.remove] is called for each fragment added to the FragmentManager.
 *
 * Must be called from main thread.
 *
 * @param bottomFragmentTag - fragment on the bottom, which is not on the back stack and should not be removed
 */
fun FragmentManager.safeClearBackStack(bottomFragmentTag: String? = null) =
    ensureMainThread {
        fragments.filter { it.tag != bottomFragmentTag }
            .forEach {
                inTransaction { remove(it) }
            }
        popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

fun FragmentManager.peekBackStack() = if (backStackEntryCount > 0) getBackStackEntryAt(backStackEntryCount - 1) else null

fun FragmentManager.peekBackStackFragment() = peekBackStack()?.let { findFragmentByTag(it.name) }

fun FragmentManager.isOnBackStack(backStackTag: String) = (0 until backStackEntryCount).any { getBackStackEntryAt(it).name == backStackTag }

private fun ensureMainThread(block: () -> Unit) {
    val mainLooper = Looper.getMainLooper()

    if (mainLooper.thread == Thread.currentThread()) block() else Handler(mainLooper).post { block() }
}
