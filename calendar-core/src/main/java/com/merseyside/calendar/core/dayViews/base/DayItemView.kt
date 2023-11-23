package com.merseyside.calendar.core.dayViews.base

import android.R.attr.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.kotlin.extensions.isNotZero
import com.merseyside.merseyLib.kotlin.extensions.isZero
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.kotlin.utils.safeLet
import com.merseyside.utils.attributes.*
import com.merseyside.utils.ext.getColorForState
import com.merseyside.utils.view.ext.*
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
        defStyleAttr
    )

    protected val currentState: Int?
        get() = getCurrentDrawableState(DRAWABLE_VIEW_STATES)

    private var textSize by attrs.dimension(R.styleable.DayItemView_dayTextSize)
    private var textFontFamily by attrs.resourceOrNull(R.styleable.DayItemView_dayTextFontFamily)
    private val textStyle by attrs.enum(
        R.styleable.DayItemView_dayTextStyle,
        defaultValue = TextStyle.NORMAL
    ) { TextStyle.getByIndex(it) }

    private val defaultTextColor by attrs.colorStateList(R.styleable.DayItemView_dayTextColor)
    private val defaultBackgroundColor by attrs.colorStateList(R.styleable.DayItemView_dayBackgroundColor)

    /* Stroke */
    private val backgroundStrokeColor by attrs.colorStateListOrNull(R.styleable.DayItemView_dayBackgroundStrokeColor)
    private val backgroundStrokeWidth by attrs.dimensionOrNull(R.styleable.DayItemView_dayBackgroundStrokeWidth)

    /* Weekend */
    protected val weekendTextColor by attrs.colorStateListOrNull(R.styleable.DayItemView_dayWeekendTextColor)
    protected val weekendBackgroundColor by attrs.colorStateListOrNull(R.styleable.DayItemView_dayWeekendBackgroundColor)

    protected var isWeekend = false
        set(value) {
            if (field != value) {
                field = value
                updateTextPaint(defaultTextPaint)
            }
        }

    private var cornerRadius by attrs.dimension(R.styleable.DayItemView_dayCornerRadius)

    /**
     * In fact this how big viewRect should be
     */
    private val desiredContentWidth by attrs.dimensionPixelSize(R.styleable.DayItemView_dayContentWidth)
    private val desiredContentHeight by attrs.dimensionPixelSize(R.styleable.DayItemView_dayContentHeight)

    private val contentWidth: Int by lazy { calculateContentWidth() }
    private val contentHeight: Int by lazy { calculateContentHeight() }


    protected var contentHorizontalPadding by attrs.dimensionPixelSize(R.styleable.DayItemView_dayContentHorizontalPadding)
    protected var contentVerticalPadding by attrs.dimensionPixelSize(R.styleable.DayItemView_dayContentVerticalPadding)

    protected var cropCircle by attrs.bool(R.styleable.DayItemView_dayCropCircle)

//    protected var viewElevation by attrs.dimension(R.styleable.DayItemView_dayElevation)

    init {
        attrs.recycle()
    }

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

    protected val backgroundStrokePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 0F
        }.also { updateBackgroundStrokePaint(it) }
    }

    protected val defaultTextPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.LEFT
            textSize = this@DayItemView.textSize

            val typeface = Typeface.create(
                safeLet(textFontFamily) { setTypeface(ResourcesCompat.getFont(context, it)) }
                    ?: Typeface.DEFAULT,
                textStyle!!.index
            )
            setTypeface(typeface)

        }.also { updateTextPaint(it) }
    }


    private val contentRect = Rect()

    // whole view rect
    private val backgroundRect = Rect()

    open fun calculateContentRect(rect: Rect, newWidth: Int, newHeight: Int) {
        if (textSize.isZero()) textSize = contentHeight.toFloat() / 2

        val viewCenter = Point(newWidth / 2, newHeight / 2)
        with(rect) {
            set(viewCenter)
            expand(contentWidth / 2, contentHeight / 2)
        }
    }

    /**
     * Must be called after view measured!
     */
    @SuppressLint("CheckResult")
    open fun calculateBackgroundRect(backgroundRect: Rect, newWidth: Int, newHeight: Int) {

        with(backgroundRect) {
            set(contentRect)
            expand(contentHorizontalPadding, contentVerticalPadding)
        }


        getRect().let { // check background rect isn't bigger than whole view.
            // If so then intersect it with view rect
            backgroundRect.intersect(it)
        }

        if (cropCircle) {
            backgroundRect.insetToSquare()

            cornerRadius = backgroundRect.width().toFloat()

            backgroundRect.width()
            backgroundRect.height()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        logMeasureSpec(widthMeasureSpec, "width")
//        logMeasureSpec(heightMeasureSpec, "item height")
//        logMsg("MeasureSpec", "<--->")

        val width = measureWithDesiredSize(widthMeasureSpec, ::getDesiredWidth)
        val height = measureWithDesiredSize(heightMeasureSpec, ::getDesiredHeight)

        onMeasured(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        calculateContentRect(contentRect, w, h)
        calculateBackgroundRect(backgroundRect, w, h)

        outlineProvider = DayOutlineProvider(backgroundRect, cornerRadius)
    }

    open fun getDesiredWidth(measureSpec: Int): Int {
        return Int.MAX_VALUE
    }

    open fun getDesiredHeight(measureSpec: Int): Int {
        return desiredContentHeight + contentVerticalPadding * 2 + paddingTop + paddingBottom
    }

    /* Call it only in onMeasure() */
    @CallSuper
    open fun onMeasured(measuredWidth: Int, measuredHeight: Int) {
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    open fun Canvas.drawBackground(
        rect: Rect,
        backgroundFillPaint: Paint,
        backgroundStrokePaint: Paint
    ) {
        drawBackgroundRect(rect, cornerRadius, backgroundFillPaint)
        if (isStrokeAvailable()) {
            drawBackgroundStrokeRect(rect, cornerRadius, backgroundStrokePaint)
        }
    }

    abstract fun Canvas.drawContent(rect: Rect, numberPaint: Paint)

    abstract fun Canvas.drawForeground()

    abstract fun Canvas.drawBackgroundRect(
        rect: Rect,
        cornerRadius: Float,
        backgroundFillPaint: Paint
    )

    abstract fun Canvas.drawBackgroundStrokeRect(
        rect: Rect,
        cornerRadius: Float,
        backgroundStrokePaint: Paint,
    )

    final override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isValid()) {
            with(canvas) {
                drawBackground(backgroundRect, backgroundPaint, backgroundStrokePaint)
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
        paint.color = getColorForStateOrDefault(textColor, state)
    }

    /**
     * Set text color by default
     */
    @CallSuper
    protected open fun updateBackgroundPaint(paint: Paint, state: Int? = currentState) {
        paint.color = getColorForStateOrDefault(backgroundColor, state)
    }

    @CallSuper
    protected open fun updateBackgroundStrokePaint(paint: Paint, state: Int? = currentState) {
        safeLet(
            backgroundStrokeWidth,
            getColorForState(backgroundStrokeColor, state)
        ) { width, color ->
            with(paint) {
                strokeWidth = width
                this.color = color
            }
        }
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
        updateBackgroundPaint(backgroundPaint, state)
        updateBackgroundStrokePaint(backgroundStrokePaint, state)
        updateTextPaint(defaultTextPaint, state)
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
            MeasureSpec.EXACTLY -> {
                if (size.isNotZero()) size
                else desireSizeBlock(measureSpec)
            }
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
    protected fun getColorForState(
        colorStateList: ColorStateList?,
        state: Int? = currentState
    ): Int? {
        return safeLet(colorStateList, state) { list, s ->
            list.getColorForState(s)
        }
    }

    @ColorInt
    protected fun getColorForStateOrDefault(
        colorStateList: ColorStateList,
        state: Int? = currentState
    ): Int {
        return getColorForState(colorStateList, state) ?: Color.GREEN
    }

    private fun calculateContentWidth(): Int {
        val maxContentWidth = width - contentHorizontalPadding * 2
        return min(maxContentWidth, desiredContentWidth)
    }

    private fun calculateContentHeight(): Int {
        val maxContentHeight = height - contentVerticalPadding * 2
        return min(maxContentHeight, desiredContentHeight)
    }

    private fun isStrokeAvailable(): Boolean {
        return backgroundStrokeWidth != null && getColorForState(
            backgroundStrokeColor,
            currentState
        ) != null
    }

    companion object {
        //Handle only this view's states
        private val DRAWABLE_VIEW_STATES =
            intArrayOf(state_selected, state_pressed, state_enabled)
    }

    enum class TextStyle(val index: Int) {
        NORMAL(0), BOLD(1), ITALIC(2);

        companion object {
            fun getByIndex(index: Int): TextStyle? {
                return values().find {
                    it.index == index
                }
            }
        }
    }
}

private class DayOutlineProvider(
    private val rect: Rect,
    private val cornerRadius: Float
) : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline?) {
        outline?.setRoundRect(rect, cornerRadius)
    }

}