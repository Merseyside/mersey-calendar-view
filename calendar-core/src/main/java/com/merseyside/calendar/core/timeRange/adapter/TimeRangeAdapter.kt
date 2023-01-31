package com.merseyside.calendar.core.timeRange.adapter

import com.merseyside.adapters.SimpleAdapter
import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.holder.TypedBindingHolder
import com.merseyside.calendar.core.BR
import com.merseyside.calendar.core.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.time.ext.logHuman
import com.merseyside.merseyLib.time.ranges.TimeRange

abstract class TimeRangeAdapter<T : TimeRangeViewModel>(adapterConfig: AdapterConfig<TimeRange, T>) :
    SimpleAdapter<TimeRange, T>(adapterConfig) {

    override fun getBindingVariable() = BR.model
}