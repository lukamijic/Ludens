package com.ludens.androidApp.ui.extensions

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleableRes

inline fun AttributeSet.withTypedArray(context: Context, @StyleableRes attrs: IntArray, @AttrRes defStyleAttr: Int, block: TypedArray.() -> Unit) {
    with(context.obtainStyledAttributes(this, attrs, defStyleAttr, 0)) {
        block()
        recycle()
    }
}

inline fun AttributeSet.withTypedArray(context: Context, @StyleableRes attrs: IntArray, block: TypedArray.() -> Unit) {
    with(context.obtainStyledAttributes(this, attrs, 0, 0)) {
        block()
        recycle()
    }
}

inline fun TypedArray.withInt(index: Int, invalidValue: Int = -1, block: (Int) -> Unit) {
    getInt(index, invalidValue).let {
        if (it != invalidValue) {
            block(it)
        }
    }
}

inline fun TypedArray.withString(index: Int, defaultValue: String = "", block: (String) -> Unit) {
    getString(index)?.let {
        block(it)
    } ?: block(defaultValue)
}

inline fun TypedArray.withColor(index: Int, defaultValue: Int = -1, block: (Int) -> Unit) {
    getColor(index, defaultValue).let {
        if (it != defaultValue) {
            block(it)
        }
    }
}

inline fun TypedArray.withDimensionPixelSize(index: Int, defaultValue: Int = 0, block: (Int) -> Unit) {
    block(getDimensionPixelSize(index, defaultValue))
}

inline fun TypedArray.withFloat(index: Int, invalidValue: Float = Float.MIN_VALUE, block: (Float) -> Unit) {
    getFloat(index, invalidValue).let {
        if (it != invalidValue) {
            block(it)
        }
    }
}

inline fun TypedArray.withDrawable(index: Int, block: (Drawable) -> Unit) {
    getDrawable(index)?.let {
        block(it)
    }
}

inline fun TypedArray.withResourceId(index: Int,defaultValue: Int, block: (Int) -> Unit) {
    block(getResourceId(index, defaultValue))
}
