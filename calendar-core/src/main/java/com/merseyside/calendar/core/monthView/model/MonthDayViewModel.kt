package com.merseyside.calendar.core.monthView.model

import com.merseyside.adapters.core.feature.selecting.SelectState
import com.merseyside.calendar.core.numberView.model.NumberDayViewModel
import com.merseyside.merseyLib.time.ranges.TimeRange

open class MonthDayViewModel(
    item: TimeRange,
    var isOutMonthDay: Boolean = false
) : NumberDayViewModel(item) {

    override val selectState = SelectState(selectable = !isOutMonthDay)
}