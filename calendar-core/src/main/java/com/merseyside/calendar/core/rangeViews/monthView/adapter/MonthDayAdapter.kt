package com.merseyside.calendar.core.rangeViews.monthView.adapter

import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.calendar.core.dayViews.numberView.adapter.NumberDayAdapter
import com.merseyside.calendar.core.rangeViews.monthView.model.MonthDayViewModel
import com.merseyside.calendar.core.rangeViews.monthView.model.OutMonthDayViewModel
import com.merseyside.merseyLib.time.ext.contains
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange

abstract class MonthDayAdapter<ViewModel: MonthDayViewModel>(
    config: AdapterConfig<TimeRange, ViewModel>,
    private val monthRange: MonthRange,
    private val isPrevMonthDayVisible: Boolean
) : NumberDayAdapter<ViewModel>(config) {

    override fun createItemViewModel(item: TimeRange): ViewModel {
        return if (!isOutMonthDay(item)) {
            MonthDayViewModel(item)
        } else {
            OutMonthDayViewModel(item, isPrevMonthDayVisible)
        } as ViewModel
    }

    protected fun isOutMonthDay(timeRange: TimeRange): Boolean {
        return !monthRange.contains(timeRange.start)
    }
}