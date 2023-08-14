package com.merseyside.calendar.core.rangeViews.base.timeRange.callback

import com.merseyside.merseyLib.time.ranges.TimeRange

fun interface TimeSelectListener {

    fun onSelect(timeRange: TimeRange, isSelect: Boolean, isSelectByUser: Boolean)
}