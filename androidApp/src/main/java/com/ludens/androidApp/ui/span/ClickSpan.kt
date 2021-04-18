package com.ludens.androidApp.ui.span

import android.text.style.ClickableSpan
import android.view.View

class ClickSpan(val clickAction: () -> Unit) : ClickableSpan() {

    override fun onClick(widget: View) {
        clickAction()
    }
}
