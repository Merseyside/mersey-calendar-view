package com.merseyside.calendar.views.rangeViews.weekDaysView.view

import android.content.Context
import android.util.AttributeSet
import com.merseyside.calendar.core.rangeViews.weekDayView.WeekDaysView
import com.merseyside.calendar.core.rangeViews.weekDayView.adapter.WeekdayAdapter
import com.merseyside.calendar.core.rangeViews.weekDayView.model.WeekdayViewModel
import com.merseyside.calendar.core.recycler.timeUnitRecyclerView.TimeUnitRecyclerView
import com.merseyside.calendar.views.R
import com.merseyside.calendar.views.databinding.ViewWeekDayTimeUnitBinding
import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.utils.delegate.getValue
import com.merseyside.utils.delegate.viewBinding

open class SimpleWeekDaysView(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) :
    WeekDaysView<WeekdayViewModel>(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attributeSet: AttributeSet) : this(
        context,
        attributeSet,
        R.attr.weekdaysViewStyle
    )

    private val binding: ViewWeekDayTimeUnitBinding by viewBinding(R.layout.view_week_day_time_unit)
    override val recycler: TimeUnitRecyclerView
        get() = binding.recyclerWeekDay

    override fun getTimeRangeAdapter(timeRange: WeekRange, pattern: String): WeekdayAdapter {
        return WeekdayAdapter(pattern)
    }

    init {
        setTime(Time.nowGMT)
    }
}