package com.merseyside.calendar.core.rangeViews.numberWeekDaysWeekView

import android.content.Context
import android.util.AttributeSet
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.rangeViews.base.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.rangeViews.numberWeekDaysWeekView.model.NumberWeekDayViewModel
import com.merseyside.calendar.core.rangeViews.weekView.WeekView
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.utils.attributes1.AttributeHelper
import com.merseyside.utils.attributes1.int

abstract class NumberWeekDaysWeekView<ViewModel : NumberWeekDayViewModel>(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : WeekView<ViewModel>(context, attributeSet, defStyleAttr) {

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.NumberWeekDaysWeekView,
        defStyleAttr,
        0
    )

    private val weekDayLength by attrs.int(R.styleable.NumberWeekDaysWeekView_numberWeekDayLength)

    protected val pattern: String by lazy {
        "E".repeat(weekDayLength)
    }

    abstract override fun provideAdapter(timeRange: WeekRange): TimeRangeAdapter<ViewModel>
}