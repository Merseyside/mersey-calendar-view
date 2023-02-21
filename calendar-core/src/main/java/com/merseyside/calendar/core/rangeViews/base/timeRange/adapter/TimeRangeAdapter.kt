package com.merseyside.calendar.core.rangeViews.base.timeRange.adapter

import com.merseyside.adapters.SimpleAdapter
import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.calendar.core.BR
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.time.ranges.TimeRange

abstract class TimeRangeAdapter<T : TimeRangeViewModel>(adapterConfig: AdapterConfig<TimeRange, T>) :
    SimpleAdapter<TimeRange, T>(adapterConfig) {

    init {
        setHasStableIds(true)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getBindingVariable() = BR.model
}