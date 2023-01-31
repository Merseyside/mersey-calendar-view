package com.merseyside.calendar.core.weekDayView.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.timeRange.view.DayItemView
import com.merseyside.calendar.core.weekDayView.model.WeekdayViewModel
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.time.ext.getHuman
import com.merseyside.merseyLib.time.units.DayOfWeek
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.int
import com.merseyside.utils.view.ext.getTextHeight
import com.merseyside.utils.view.ext.getTextWidth

class WeekdayView(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : DayItemView(context, attributeSet, defStyleAttr) {

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.WeekdayView,
        "WeekdayView",
        defStyleAttr,
        0,
        "weekday"
    )

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        R.attr.weekdayViewStyle
    )

    private var index by attrs.int(INVALID_INDEX)
    private var dayOfWeek: String =
        if (index != INVALID_INDEX) DayOfWeek.getByIndex(index).getHuman() else ""

    private val desiredWidth by lazy { Int.MAX_VALUE }
    private val desiredHeight by lazy { textPaint.getTextHeight("Mo") }

    override fun getTextContent(): String {
        return dayOfWeek
    }

    override fun Canvas.drawForeground() {}

    override fun Canvas.drawBackgroundRect(rect: Rect, paint: Paint) {}

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