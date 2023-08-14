package com.merseyside.calendar.core.rangeViews.weekDayView.adapter

import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.rangeViews.base.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.rangeViews.weekDayView.model.WeekdayViewModel
import com.merseyside.merseyLib.time.ranges.TimeRange

class WeekdayAdapter(
    adapterConfig: AdapterConfig<TimeRange, WeekdayViewModel>,
    private val pattern: String
) : TimeRangeAdapter<WeekdayViewModel>(adapterConfig) {

    override fun createItemViewModel(item: TimeRange) = WeekdayViewModel(item, pattern)
    override fun getLayoutIdForViewType(viewType: Int) = R.layout.view_week_day

    companion object {
        operator fun invoke(
            pattern: String,
            configure: AdapterConfig<TimeRange, WeekdayViewModel>.() -> Unit = {}
        ): WeekdayAdapter {
            return initAdapter(::WeekdayAdapter, pattern, configure)
        }
    }
}