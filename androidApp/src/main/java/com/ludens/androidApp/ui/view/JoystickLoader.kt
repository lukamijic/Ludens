package com.ludens.androidApp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.ludens.androidApp.R

class JoystickLoader : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.joystick_loader_background))

        inflate(context, R.layout.view_joystick_loader, this)
    }
}
