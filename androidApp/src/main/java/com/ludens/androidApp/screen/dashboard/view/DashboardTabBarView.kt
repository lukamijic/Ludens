package com.ludens.androidApp.screen.dashboard.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import com.ludens.androidApp.databinding.ViewDashboardTabBarBinding
import com.ludens.androidApp.screen.dashboard.DashboardNavigationItem
import com.ludens.androidApp.ui.extensions.deselect
import com.ludens.androidApp.ui.extensions.onClick
import com.ludens.androidApp.ui.extensions.select


private const val KEY_ACTIVE_ITEM_INDEX = "KEY_ACTIVE_ITEM_INDEX"
private const val KEY_SUPER_STATE = "KEY_SUPER_STATE"
private const val HOME_INDEX = 0
private const val USER_INDEX = 1

class DashboardTabBarView : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var binding: ViewDashboardTabBarBinding = ViewDashboardTabBarBinding.inflate(
        LayoutInflater.from(context), this
    )

    private var itemSelectedListener: ((DashboardNavigationItem) -> Unit)? = null
    private var searchListener: (() -> Unit)? = null

    private var shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        setShadowLayer(20f, 0f, 0f, Color.parseColor("#77888888"))
        style = Paint.Style.FILL_AND_STROKE
        color = Color.TRANSPARENT
    }

    private val shadowPath = Path()

    init {
        clipChildren = false
        clipToPadding = false

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        with(binding) {
            home.onClick { selectHome() }
            user.onClick { selectUser() }
            searchButton.onClick { searchListener?.invoke() }
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            when (val index = state.getInt(KEY_ACTIVE_ITEM_INDEX)) {
                HOME_INDEX -> selectHome()
                USER_INDEX -> selectUser()
                else -> throw IllegalStateException("Unknown bottom tab bar item with index $index")
            }
            super.onRestoreInstanceState(state.getParcelable(KEY_SUPER_STATE))
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onSaveInstanceState(): Parcelable = bundleOf(
        KEY_SUPER_STATE to super.onSaveInstanceState(),
        KEY_ACTIVE_ITEM_INDEX to if (binding.home.isSelected) HOME_INDEX else USER_INDEX
    )

    override fun dispatchDraw(canvas: Canvas) {
        canvas.drawPath(shadowPath, shadowPaint)
        super.dispatchDraw(canvas)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateShadowPath(w, h)
    }

    override fun willNotDraw(): Boolean = false

    fun onSelectedBottomBarItem(listener: (DashboardNavigationItem) -> Unit) {
        itemSelectedListener = listener
    }

    fun onSearchButtonClicked(listener: () -> Unit) {
        searchListener = listener
    }

    private fun selectHome() = with(binding) {
        home.select()
        user.deselect()
        itemSelectedListener?.invoke(DashboardNavigationItem.HOME)
    }

    private fun selectUser() = with(binding) {
        user.select()
        home.deselect()
        itemSelectedListener?.invoke(DashboardNavigationItem.USER)
    }

    private fun calculateShadowPath(w: Int, h: Int) {
        shadowPath.reset()

        shadowPath.moveTo(0f, h * 0.238f)
        shadowPath.cubicTo(0f, h * 0.238f, w * 0.323f, h * 0.238f, w * 0.368f, h * 0.25f)
        shadowPath.cubicTo(w * 0.413f, h * 0.262f, w * 0.418f, h * 0.537f, w * 0.498f, h * 0.537f)
        shadowPath.cubicTo(w * 0.577f, h * 0.537f, w * 0.587f, h * 0.262f, w * 0.637f, h * 0.25f)
        shadowPath.cubicTo(w * 0.685f, h * 0.25f, w.toFloat(), h * 0.238f, w.toFloat(), h * 0.238f)

        shadowPath.lineTo(w.toFloat(), h.toFloat())
        shadowPath.lineTo(0f, h.toFloat())
        shadowPath.close()
    }
}
