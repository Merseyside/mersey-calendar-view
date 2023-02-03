package com.merseyside.calendar.core.rangeViews.monthView.model

import com.merseyside.merseyLib.time.ranges.TimeRange

class OutMonthDayViewModel(
    item: TimeRange,
    private val isItemVisible: Boolean
) : MonthDayViewModel(item, isOutMonthDay = true) {

    override var isVisible: Boolean = isItemVisible
        set(value) {
            field = value && isItemVisible
        }
}