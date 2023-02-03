package com.merseyside.calendar.views.dayViews.numberWeekDayView.view

import android.R.attr.*
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import com.merseyside.calendar.core.dayViews.numberView.model.NumberDayViewModel
import com.merseyside.calendar.core.dayViews.numberView.view.NumberDayView
import com.merseyside.calendar.views.R
import com.merseyside.calendar.views.dayViews.numberWeekDayView.model.NumberWeekDayViewModel
import com.merseyside.merseyLib.time.ext.getHuman
import com.merseyside.merseyLib.time.units.DayOfWeek
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.colorStateList
import com.merseyside.utils.delegate.dimension
import com.merseyside.utils.delegate.int
import com.merseyside.utils.view.canvas.HorizontalAlign
import com.merseyside.utils.view.canvas.VerticalAlign
import com.merseyside.utils.view.ext.drawTextOnBaseline

class NumberWeekDayView(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : NumberDayView(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        R.attr.numberWeekDayViewStyle
    )

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.NumberWeekDayView,
        "NumberWeekDayView",
        defStyleAttr,
        0,
        "number"
    )

    protected var weekDayIndex by attrs.int(INVALID_INDEX)
    private var dayOfWeek: String =
        if (weekDayIndex != INVALID_INDEX) DayOfWeek.getByIndex(weekDayIndex).getHuman() else ""

    protected var weekDayTextSize by attrs.dimension()
    protected val weekDayTextColor by attrs.colorStateList(defaultValue = textColor)

    protected val weekDayTextPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.LEFT
            textSize = weekDayTextSize
        }.also { updateWeekDayTextPaint(it) }
    }

    override fun Canvas.drawContent(rect: Rect, numberPaint: Paint) {
        with(rect) {
            drawTextOnBaseline( // draw dayOfWeek text on top
                text = dayOfWeek,
                horizontalAlign = HorizontalAlign.CENTER,
                verticalAlign = VerticalAlign.TOP,
                paint = weekDayTextPaint
            )

            drawTextOnBaseline( // draw number text on bottom
                text = dayNumber.toString(),
                horizontalAlign = HorizontalAlign.CENTER,
                verticalAlign = VerticalAlign.BOTTOM,
                paint = numberPaint
            )
        }
    }

    override fun updateBackgroundPaint(paint: Paint, state: Int?) {
        super.updateBackgroundPaint(paint, state)

        with(paint) {
            when (state) {
                state_enabled, -> {
                    style = Paint.Style.STROKE
                    strokeWidth = 2f
                }

                else -> {
                    style = Paint.Style.FILL
                    strokeWidth = 0f
                }
            }
        }
    }

    override fun onStateChanged(state: Int?) {
        super.onStateChanged(state)
        updateWeekDayTextPaint(weekDayTextPaint, state)
    }

    private fun updateWeekDayTextPaint(paint: Paint, state: Int? = currentState) {
        paint.color = getColorForState(weekDayTextColor, state)
    }

    fun applyModel(model: NumberWeekDayViewModel) {
        dayOfWeek = model.humanDayOfWeek
        applyModel(model as NumberDayViewModel)
    }

    companion object {
        private const val INVALID_INDEX = -1
    }
}