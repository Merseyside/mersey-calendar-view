package com.merseyside.calendar.views.dayViews.numberWeekDayView.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import com.merseyside.calendar.core.dayViews.numberView.model.NumberDayViewModel
import com.merseyside.calendar.core.dayViews.numberView.view.NumberDayView
import com.merseyside.calendar.core.rangeViews.numberWeekDaysWeekView.model.NumberWeekDayViewModel
import com.merseyside.calendar.views.R
import com.merseyside.merseyLib.time.ext.getHuman
import com.merseyside.merseyLib.time.units.DayOfWeek
import com.merseyside.utils.attributes1.*
import com.merseyside.utils.view.canvas.HorizontalAlign
import com.merseyside.utils.view.canvas.VerticalAlign
import com.merseyside.utils.view.ext.drawTextOnBaseline

open class NumberWeekDayView(
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
        defStyleAttr,
        0
    )

    protected var weekDayIndex by attrs.int(
        R.styleable.NumberWeekDayView_numberWeekDayIndex,
        defaultValue = INVALID_INDEX
    )

    private var dayOfWeek: String =
        if (weekDayIndex != INVALID_INDEX) DayOfWeek.getByIndex(weekDayIndex).getHuman() else ""

    protected var weekDayTextSize by attrs.dimension(R.styleable.NumberWeekDayView_numberWeekDayTextSize)
    protected val weekDayTextColor by attrs.colorStateList(
        R.styleable.NumberWeekDayView_numberWeekDayTextColor,
        defaultValue = textColor
    )

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

    override fun onStateChanged(state: Int?) {
        super.onStateChanged(state)
        updateWeekDayTextPaint(weekDayTextPaint, state)
    }

    private fun updateWeekDayTextPaint(paint: Paint, state: Int? = currentState) {
        paint.color = getColorForStateOrDefault(weekDayTextColor, state)
    }

    fun applyModel(model: NumberWeekDayViewModel) {
        dayOfWeek = model.humanDayOfWeek
        applyModel(model as NumberDayViewModel)
    }

    companion object {
        private const val INVALID_INDEX = -1
    }
}