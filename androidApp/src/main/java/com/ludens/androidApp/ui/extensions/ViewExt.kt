package com.ludens.androidApp.ui.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.updatePadding

private const val COMMON_ANIMATION_DURATION = 300L

fun View.select(isSelected: Boolean = true) {
    this.isSelected = isSelected
}

fun View.deselect() = select(false)

fun View.show(shouldShow: Boolean = true) {
    visibility = if (shouldShow) View.VISIBLE else View.GONE
}

fun View.hide() = show(false)

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.enable(isEnabled: Boolean = true) {
    this.isEnabled = isEnabled
}

fun View.disable() = enable(isEnabled = false)

@SuppressLint("ClickableViewAccessibility")
fun View.disableTouches() {
    setOnTouchListener { _, _ -> true }
}

fun View.fade(show: Boolean, withStartDelay: Long = 0, duration: Long = COMMON_ANIMATION_DURATION) {
    if (show) {
        fadeIn(withStartDelay, duration)
    } else {
        fadeOut(withStartDelay, duration)
    }
}

fun View.fadeIn(withStartDelay: Long = 0, duration: Long = COMMON_ANIMATION_DURATION) {
    animate()
        .setDuration(duration)
        .setStartDelay(withStartDelay)
        .withStartAction { visibility = View.VISIBLE }
        .alpha(1f)
}

fun View.fadeOut(withStartDelay: Long = 0, duration: Long = COMMON_ANIMATION_DURATION) {
    animate()
        .setDuration(duration)
        .setStartDelay(withStartDelay)
        .alpha(0f)
        .withEndAction { visibility = View.GONE }
}

fun View.showKeyboard() {
    requestFocus()
    (context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .toggleSoftInputFromWindow(windowToken, InputMethodManager.SHOW_FORCED, 0)
}

fun View.hideKeyboard() {
    clearFocus()
    (context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}

fun View.onClick(action: () -> Unit) {
    setOnClickListener { action() }
}

fun View.applySystemInsets() {
    setOnApplyWindowInsetsListener { v, insets ->
        v.updatePadding(
            top = insets.systemWindowInsetTop,
            bottom = insets.systemWindowInsetBottom
        )
        insets
    }
}

fun View.applyTopSystemInsets() {
    setOnApplyWindowInsetsListener { v, insets ->
        v.updatePadding(
            top = insets.systemWindowInsetTop
        )
        insets
    }
}

fun View.applyBottomSystemInsets() {
    setOnApplyWindowInsetsListener { v, insets ->
        v.updatePadding(
            bottom = insets.systemWindowInsetBottom

        )
        insets
    }
}
