package com.merseyside.calendar.core.numberView.adapter

import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.calendar.core.numberView.model.NumberDayViewModel
import com.merseyside.calendar.core.timeRange.adapter.TimeRangeAdapter
import com.merseyside.merseyLib.time.ranges.TimeRange

abstract class NumberDayAdapter<T : NumberDayViewModel>(adapterConfig: AdapterConfig<TimeRange, T>) :
    TimeRangeAdapter<T>(adapterConfig) {

    abstract override fun createItemViewModel(item: TimeRange): T
}