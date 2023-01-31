package com.merseyside.calendar.core.timeRange.provider

import com.merseyside.calendar.core.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.kotlin.extensions.forEachEntry
import com.merseyside.merseyLib.time.ext.logHuman
import com.merseyside.merseyLib.time.ranges.TimeRange

class TimeRangeAdapterProvider<TR : TimeRange, Adapter : TimeRangeAdapter<out TimeRangeViewModel>> {

    private val adapterMap: MutableMap<TR, Adapter> = mutableMapOf()
    lateinit var currentAdapter: Adapter

    private lateinit var callback: ProviderCallback<TR, Adapter>

    fun setProviderCallback(callback : ProviderCallback<TR, Adapter>) {
        this.callback = callback
    }

    fun getTimeRangeAdapter(timeRange: TR): Adapter {
        return run {
            findAdapter(timeRange) ?: callback.createAdapterWithTimeRange(timeRange).also { adapter ->

                adapterMap[timeRange] = adapter
//                var tempRange = timeRange
//                (0..20).forEach { _ ->
//                    tempRange = callback.getNextTimeRange(tempRange)
//                    callback.createAdapterWithTimeRange(tempRange).also { adapterMap[tempRange] = it }
//                }
            }
        }.also { currentAdapter = it }
    }

    private fun findAdapter(timeRange: TimeRange): Adapter? {
        return adapterMap[timeRange]
    }


    interface ProviderCallback<TR : TimeRange, Adapter : TimeRangeAdapter<out TimeRangeViewModel>> {
        fun createAdapterWithTimeRange(timeRange: TR): Adapter

        fun getNextTimeRange(timeRange: TR): TR

    }
}