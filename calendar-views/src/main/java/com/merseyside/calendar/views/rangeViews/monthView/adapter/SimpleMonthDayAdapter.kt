package com.merseyside.calendar.views.rangeViews.monthView.adapter

import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.calendar.core.rangeViews.monthView.adapter.MonthDayAdapter
import com.merseyside.calendar.core.rangeViews.monthView.model.MonthDayViewModel
import com.merseyside.calendar.views.R
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange

class SimpleMonthDayAdapter(
    config: AdapterConfig<TimeRange, MonthDayViewModel>,
    monthRange: MonthRange,
    isPrevMonthDayVisible: Boolean
) : MonthDayAdapter<MonthDayViewModel>(config, monthRange, isPrevMonthDayVisible) {

    override fun getLayoutIdForViewType(viewType: Int) = R.layout.view_month_day

    companion object {
        operator fun invoke(
            monthRange: MonthRange,
            isPrevMonthDayVisible: Boolean,
            configure: AdapterConfig<TimeRange, MonthDayViewModel>.() -> Unit
        ): SimpleMonthDayAdapter {
            return initAdapter(::SimpleMonthDayAdapter, monthRange, isPrevMonthDayVisible, configure)
        }
    }
}