package com.ludens.androidApp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.animation.BounceInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import com.ludens.androidApp.R
import com.ludens.androidApp.ui.extensions.*

private const val DEFAULT_TEXT_STYLE = R.style.Text

private const val DEFAULT_FALL_DURATION = 350L
private const val DEFAULT_FALL_DISTANCE = 500f
private const val DEFAULT_LETTER_DELAY = 50L

class FallingText : LinearLayout {

    var text = ""
        set(value) {
            field = value
            letters = value.toCharArray()
            initTextViews()
        }

    private lateinit var letters: CharArray
    private lateinit var textViews: List<TextView>
    private var textStyle: Int = DEFAULT_TEXT_STYLE

    private var fallDuration = DEFAULT_FALL_DURATION
    private var fallDistance = DEFAULT_FALL_DISTANCE
    private var fallLetterDelay = DEFAULT_LETTER_DELAY

    var onFallEndAction: () -> Unit = { }

    constructor(context: Context) : super(context) {
        parseAttributes(null, 0, 0)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
        parseAttributes(attrs, 0, 0)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        parseAttributes(attrs, defStyleAttr, 0)
    }

    private fun parseAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        attrs?.withTypedArray(context, R.styleable.FallingText, defStyleAttr) {
            withString(R.styleable.FallingText_fallingText) { text = it }
            withInt(R.styleable.FallingText_fallDistance) { fallDistance = it.toFloat() }
            withInt(R.styleable.FallingText_fallDuration) { fallDuration = it.toLong() }
            withInt(R.styleable.FallingText_letterFallDelay) { fallLetterDelay = it.toLong() }
            withResourceId(R.styleable.FallingText_fallingTextStyle, DEFAULT_TEXT_STYLE) { textStyle = it}
        }
    }

    init {
        inflate(context, R.layout.view_falling_text, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initTextViews()
    }

    private fun initTextViews() {
        removeAllViews()

        val newTextViews = mutableListOf<TextView>()
        letters.forEach {
            TextView(context).apply {
                text = it.toString()
                setTextAppearance(textStyle)
                newTextViews.add(this)
                layoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT
                ).apply {
                    gravity = Gravity.CENTER
                }
            }
        }

        textViews = newTextViews
        textViews.forEach { addView(it) }
    }

    fun fall() {
        var delay = 0L
        textViews.forEach { it.makeInvisible() }
        textViews.forEach { textView ->
            val isLast = textViews.last() === textView
            val targetY = textView.y
            textView.y = textView.y - fallDistance

            textView.animate()
                .withStartAction { textView.show() }
                .withEndAction { if(isLast) onFallEndAction() }
                .setDuration(fallDuration)
                .setStartDelay(delay)
                .translationY(targetY)
                .setInterpolator(BounceInterpolator())
                .start()

            delay += fallLetterDelay
        }
    }
}
