package com.merseyside.calendar.monthView.model

import com.merseyside.adapters.core.feature.selecting.SelectState
import com.merseyside.calendar.model.SimpleDayViewModel
import com.merseyside.merseyLib.time.ranges.TimeRange

open class MonthDayViewModel(
    item: TimeRange,
    var isOutMonthDay: Boolean = false
) : SimpleDayViewModel(item) {

    override val selectState = SelectState(selectable = !isOutMonthDay)
}