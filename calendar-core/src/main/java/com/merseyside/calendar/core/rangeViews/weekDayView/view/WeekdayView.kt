package com.merseyside.calendar.core.rangeViews.weekDayView.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.dayViews.base.DayItemView
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.rangeViews.weekDayView.model.WeekdayViewModel
import com.merseyside.merseyLib.time.ext.getHuman
import com.merseyside.merseyLib.time.units.DayOfWeek
import com.merseyside.utils.attributes1.AttributeHelper
import com.merseyside.utils.attributes1.int
import com.merseyside.utils.view.canvas.ext.drawTextCenter

class WeekdayView(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : DayItemView(context, attributeSet, defStyleAttr) {

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.WeekdayView,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        R.attr.weekdayViewStyle
    )

    private var index by attrs.int(
        R.styleable.WeekdayView_weekdayIndex,
        defaultValue = INVALID_INDEX
    )
    private var dayOfWeek: String =
        if (index != INVALID_INDEX) DayOfWeek.getByIndex(index).getHuman() else ""

    override fun Canvas.drawForeground() {}

    override fun Canvas.drawBackgroundRect(
        rect: Rect,
        cornerRadius: Float,
        backgroundFillPaint: Paint,
    ) {}

    override fun Canvas.drawBackgroundStrokeRect(
        rect: Rect,
        cornerRadius: Float,
        backgroundStrokePaint: Paint
    ) {}

    override fun Canvas.drawContent(rect: Rect, numberPaint: Paint) {
        drawTextCenter(rect, numberPaint, dayOfWeek)
    }


    override fun isValid(): Boolean {
        return true
    }

    fun applyModel(model: WeekdayViewModel) {
        dayOfWeek = model.humanDayOfWeek
        applyModel(model as TimeRangeViewModel)
    }

    companion object {
        private const val INVALID_INDEX = -1
    }
}