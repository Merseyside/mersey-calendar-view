package com.merseyside.calendar.core.dayViews.base

import android.R.attr.*
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.kotlin.extensions.isZero
import com.merseyside.merseyLib.kotlin.utils.safeLet
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.*
import com.merseyside.utils.ext.getColorForState
import com.merseyside.utils.view.ext.expand
import com.merseyside.utils.view.ext.getCurrentDrawableState
import com.merseyside.utils.view.ext.set
import kotlin.math.min

abstract class DayItemView(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : View(context, attributeSet, defStyleAttr) {

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.DayItemView,
        "DayItemView",
        defStyleAttr,
        0,
        "day"
    )

    protected val currentState: Int?
        get() = getCurrentDrawableState(DRAWABLE_VIEW_STATES)

    private var textSize by attrs.dimension()
    private val defaultTextColor by attrs.colorStateList(resName = "textColor")
    private val defaultBackgroundColor by attrs.colorStateList(resName = "backgroundColor")

    protected var isWeekend = false
        set(value) {
            if (field != value) {
                field = value
                updateTextPaint(defaultTextPaint)
            }
        }
    protected val weekendTextColor by attrs.colorStateListOrNull()
    protected val weekendBackgroundColor by attrs.colorStateListOrNull()

    private var cornerRadius by attrs.dimension()

    /**
     * In fact this how big viewRect should be
     */
    private val desiredContentWidth by attrs.dimensionPixelSize(resName = "contentWidth")
    private val desiredContentHeight by attrs.dimensionPixelSize(resName = "contentHeight")

    private val contentWidth: Int by lazy { calculateContentWidth() }
    private val contentHeight: Int by lazy { calculateContentHeight() }


    protected var contentHorizontalPadding by attrs.dimensionPixelSize()
    protected var contentVerticalPadding by attrs.dimensionPixelSize()

    protected var cropCircle by attrs.bool()

    open val textColor: ColorStateList
        get() {
            return if (isWeekend && weekendTextColor != null) {
                weekendTextColor!!
            } else defaultTextColor
        }

    open val backgroundColor: ColorStateList
        get() {
            return if (isWeekend && weekendBackgroundColor != null) {
                weekendBackgroundColor!!
            } else defaultBackgroundColor
        }

    protected val backgroundPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).also { updateBackgroundPaint(it) }
    }

    protected val defaultTextPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.LEFT
            textSize = this@DayItemView.textSize
        }.also { updateTextPaint(it) }
    }


    private val contentRect: Rect by lazy { Rect().apply(::calculateContentRect) }

    // whole view rect
    private val viewRect: Rect by lazy { Rect().apply(::calculateViewRect) }

    open fun calculateContentRect(rect: Rect) {
        if (textSize.isZero()) textSize = contentHeight.toFloat() / 2

        val viewCenter = Point(width / 2, height / 2)
        with(rect) {
            set(viewCenter)
            expand(contentWidth / 2, contentHeight / 2)
        }
    }

    /**
     * Must be called after view measured!
     */
    open fun calculateViewRect(rect: Rect) {

        rect.set(contentRect)
        rect.expand(contentHorizontalPadding, contentVerticalPadding)

        if (cropCircle) {
            if (rect.width() != rect.height()) {
                if (rect.width() < rect.height()) {
                    if (rect.height() <= width) rect.expand(rect.height() - rect.width(), 0)
                    else rect.inset(0, (rect.height() - rect.width()) / 2)
                } else {
                    if (rect.width() <= height) rect.expand(0, rect.width() - rect.height())
                    else rect.inset((rect.width() - rect.height()) / 2, 0)
                }
            }

            cornerRadius = rect.width().toFloat()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        logMeasureSpec(widthMeasureSpec, "width")
//        logMeasureSpec(heightMeasureSpec, "height")
//        logMsg("MeasureSpec", "<--->")

        val width = measureWithDesiredSize(widthMeasureSpec, ::getDesiredWidth)
        val height = measureWithDesiredSize(heightMeasureSpec, ::getDesiredHeight)

        onMeasured(width, height)
    }

    open fun getDesiredWidth(measureSpec: Int): Int {
        return Int.MAX_VALUE
    }

    open fun getDesiredHeight(measureSpec: Int): Int {
        return desiredContentHeight + contentVerticalPadding * 2
    }

    /* Call it only in onMeasure() */
    @CallSuper
    open fun onMeasured(measuredWidth: Int, measuredHeight: Int) {
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    open fun Canvas.drawBackground(rect: Rect, paint: Paint) {
        if (paint.style != Paint.Style.FILL) {

        }
        drawBackgroundRect(rect, cornerRadius, paint)
    }

    abstract fun Canvas.drawContent(rect: Rect, numberPaint: Paint)

    abstract fun Canvas.drawForeground()

    abstract fun Canvas.drawBackgroundRect(rect: Rect, cornerRadius: Float, paint: Paint)

    final override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isValid()) {
            with(canvas) {
                drawBackground(viewRect, backgroundPaint)
                drawContent(contentRect, defaultTextPaint)
                drawForeground()
            }
        }
    }

    protected abstract fun isValid(): Boolean

    /**
     * Set text color by default
     */
    @CallSuper
    protected open fun updateTextPaint(paint: Paint, state: Int? = currentState) {
        paint.color = getColorForState(textColor, state)
    }

    /**
     * Set text color by default
     */
    @CallSuper
    protected open fun updateBackgroundPaint(paint: Paint, state: Int? = currentState) {
        paint.color = getColorForState(backgroundColor, state)
    }

    /**
     * Overrides view's state setters in order to change paints colors from ColorStateLists
     */

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        onStateChanged()
    }

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        onStateChanged()
        invalidate()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        onStateChanged()
    }

    @CallSuper
    protected open fun onStateChanged(state: Int? = currentState) {
        updateBackgroundPaint(backgroundPaint)
        updateTextPaint(defaultTextPaint)
    }

    /**
     * Inspired by LayoutManager.chooseSize
     */
    private fun measureWithDesiredSize(
        measureSpec: Int,
        desireSizeBlock: (measureSpec: Int) -> Int
    ): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)

        return when (mode) {
            MeasureSpec.EXACTLY -> size
            MeasureSpec.AT_MOST -> min(desireSizeBlock(measureSpec), size)
            MeasureSpec.UNSPECIFIED -> desireSizeBlock(measureSpec)

            else -> throw IllegalArgumentException()
        }
    }

    private fun makeMeasureSpecWithDesiredSize(
        measureSpec: Int,
        desireSizeBlock: (measureSpec: Int) -> Int
    ): Int {

        return MeasureSpec.makeMeasureSpec(
            measureWithDesiredSize(measureSpec, desireSizeBlock),
            MeasureSpec.getMode(measureSpec)
        )
    }

    fun applyModel(model: TimeRangeViewModel) {
        isWeekend = model.isWeekend
        invalidate()
    }

    @ColorInt
    protected fun getColorForState(colorStateList: ColorStateList, state: Int?): Int {
        return safeLet(state) {
            colorStateList.getColorForState(it)
        } ?: colorStateList.defaultColor
    }

    private fun calculateContentWidth(): Int {
        val maxContentWidth = width - contentHorizontalPadding * 2
        return min(maxContentWidth, desiredContentWidth)
    }

    private fun calculateContentHeight(): Int {
        val maxContentHeight = height - contentVerticalPadding * 2
        return min(maxContentHeight, desiredContentHeight)
    }

    companion object {
        //Handle only this view's states
        private val DRAWABLE_VIEW_STATES =
            intArrayOf(state_selected, state_pressed, state_enabled)
    }
}