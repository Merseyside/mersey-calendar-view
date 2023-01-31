package com.merseyside.calendar.core.timeRange.view

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
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.kotlin.extensions.isZero
import com.merseyside.merseyLib.kotlin.logger.logMsg
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.*
import com.merseyside.utils.ext.getColorByCurrentState
import com.merseyside.utils.view.canvas.ext.drawTextCenter
import com.merseyside.utils.view.ext.set
import com.merseyside.utils.view.measure.logMeasureSpec
import kotlin.math.max
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

    protected var textSize by attrs.dimension()
    private val defaultTextColor by attrs.colorStateList(resName = "textColor")
    private val defaultBackgroundColor by attrs.colorStateList(resName = "backgroundColor")

    protected var isWeekend = false
        set(value) {
            if (field != value) {
                field = value
                updateTextPaint()
            }
        }
    protected val weekendTextColor by attrs.colorStateListOrNull()
    protected val weekendBackgroundColor by attrs.colorStateListOrNull()

    protected var cornerRadius by attrs.dimensionPixelSize()

    /**
     * In fact this how big viewRect should be
     */
    protected var contentWidth by attrs.dimensionPixelSize()
    protected var contentHeight by attrs.dimensionPixelSize()

    /**
     * Content paddings. viewRect + this paddings
     */
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
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = getColorByCurrentState(backgroundColor, VIEW_STATES)
        }
    }

    protected fun calculateTextSize(): Float {
        return if (textSize.isZero()) contentHeight.toFloat() / 2 else textSize
    }

    protected val textPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.CENTER
            color = getColorByCurrentState(textColor, VIEW_STATES)
            textSize = calculateTextSize()
        }
    }

    // whole view rect excluding paddings
    private val contentRect: Rect by lazy { Rect().apply(::calculateContentRect) }
    private val viewRect: Rect by lazy { Rect().apply(::calculateViewRect) }

    open fun calculateContentRect(rect: Rect) {
        if (textSize.isZero()) textSize = contentHeight.toFloat() / 2

        val viewCenter = Point(width / 2, height / 2)
        with(rect) {
            left = viewCenter.x
            top = viewCenter.y
            right = viewCenter.x
            bottom = viewCenter.y
        }
    }

    /**
     * Must be called after view measured!
     */
    open fun calculateViewRect(rect: Rect) {
        val viewCenter = Point(width / 2, height / 2)

        contentWidth = min(width, contentWidth)
        contentHeight = min(height, contentHeight)

        if (cropCircle) {
            val minValue = min(contentWidth, contentHeight)
            contentWidth = minValue
            contentHeight = minValue

            cornerRadius = minValue
        }

        val left = max(viewCenter.x - contentWidth / 2, 0)
        val top = max(viewCenter.y - contentHeight / 2, 0)

        val right = min(viewCenter.x + contentWidth / 2, width)
        val bottom = min(viewCenter.y + contentHeight / 2, height)
        with(rect) {
            set(
                Point(left, top),
                Point(right, bottom)
            )
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
        return contentHeight + contentVerticalPadding
    }

    /* Call it only in onMeasure() */
    @CallSuper
    open fun onMeasured(measuredWidth: Int, measuredHeight: Int) {
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    abstract fun getTextContent(): String

    open fun Canvas.drawBackground(rect: Rect, paint: Paint) {
        drawBackgroundRect(rect, paint)
    }

    open fun Canvas.drawText(rect: Rect, paint: Paint) {
        drawTextCenter(rect, paint, getTextContent())
    }

    abstract fun Canvas.drawForeground()

    abstract fun Canvas.drawBackgroundRect(rect: Rect, paint: Paint)

    final override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isValid()) {
            with(canvas) {
                drawBackground(viewRect, backgroundPaint)
                drawText(contentRect, textPaint)
                drawForeground()
            }
        }
    }

    protected abstract fun isValid(): Boolean

    protected fun updateTextPaint() {
        textPaint.color = getColorByCurrentState(textColor, VIEW_STATES)
    }

    private fun updateBackgroundPaint() {
        backgroundPaint.color = getColorByCurrentState(backgroundColor, VIEW_STATES)
    }

    /**
     * Overrides view's state setters in order to change paints colors from ColorStateLists
     */
    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        onStateChanged()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        onStateChanged()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        onStateChanged()
    }

    private fun onStateChanged() {
        updateBackgroundPaint()
        updateTextPaint()
    }

    companion object {
        //Handle only this view's states
        private val VIEW_STATES =
            intArrayOf(state_hovered, state_pressed, state_selected, state_enabled)
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
}