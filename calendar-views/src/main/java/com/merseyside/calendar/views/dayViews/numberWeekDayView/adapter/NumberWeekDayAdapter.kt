package com.merseyside.calendar.views.dayViews.numberWeekDayView.adapter

import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.calendar.core.dayViews.numberView.adapter.NumberDayAdapter
import com.merseyside.calendar.views.R
import com.merseyside.calendar.core.rangeViews.numberWeekDaysWeekView.model.NumberWeekDayViewModel
import com.merseyside.merseyLib.time.ranges.TimeRange

class NumberWeekDayAdapter(
    adapterConfig: AdapterConfig<TimeRange, NumberWeekDayViewModel>,
    private val pattern: String
) : NumberDayAdapter<NumberWeekDayViewModel>(adapterConfig) {

    override fun createItemViewModel(item: TimeRange) = NumberWeekDayViewModel(item, pattern)

    override fun getLayoutIdForPosition(position: Int) = R.layout.view_number_week_day

    companion object {
        operator fun invoke(
            pattern: String,
            configure: AdapterConfig<TimeRange, NumberWeekDayViewModel>.() -> Unit
        ): NumberWeekDayAdapter {
            return initAdapter(::NumberWeekDayAdapter, pattern, configure)

        }
    }
}