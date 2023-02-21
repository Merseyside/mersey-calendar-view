package com.merseyside.calendar.core.rangeViews.base.timeRange.provider

import com.merseyside.calendar.core.rangeViews.base.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.time.ranges.TimeRange

class TimeRangeAdapterProvider<TR : TimeRange, Adapter : TimeRangeAdapter<Model>, Model : TimeRangeViewModel> {

    private val adapterMap: MutableMap<TR, Adapter> = mutableMapOf()
    var currentAdapter: Adapter? = null

    private lateinit var callback: ProviderCallback<TR, Adapter>

    fun setProviderCallback(callback : ProviderCallback<TR, Adapter>) {
        this.callback = callback
    }

    fun getTimeRangeAdapter(timeRange: TR): Adapter {
        return run {
            findAdapter(timeRange) ?: callback.createAdapterWithTimeRange(timeRange).also { adapter ->

                adapterMap[timeRange] = adapter
            }
        }.also { currentAdapter = it }
    }

    private fun findAdapter(timeRange: TimeRange): Adapter? {
        return adapterMap[timeRange]
    }


    interface ProviderCallback<TR : TimeRange, Adapter : TimeRangeAdapter<out TimeRangeViewModel>> {
        fun createAdapterWithTimeRange(timeRange: TR): Adapter
    }
}