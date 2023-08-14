package com.merseyside.calendar.core.rangeViews.weekView.adapter

import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.dayViews.numberView.adapter.NumberDayAdapter
import com.merseyside.calendar.core.dayViews.numberView.model.NumberDayViewModel
import com.merseyside.merseyLib.time.ranges.TimeRange

class WeekDayAdapter(adapterConfig: AdapterConfig<TimeRange, NumberDayViewModel>) :
    NumberDayAdapter<NumberDayViewModel>(adapterConfig) {
    override fun createItemViewModel(item: TimeRange) = NumberDayViewModel(item)

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLayoutIdForViewType(viewType: Int) = R.layout.view_number_day

    companion object {

        operator fun invoke(configure: AdapterConfig<TimeRange, NumberDayViewModel>.() -> Unit): WeekDayAdapter {
            return initAdapter(::WeekDayAdapter, configure)
        }
    }
}