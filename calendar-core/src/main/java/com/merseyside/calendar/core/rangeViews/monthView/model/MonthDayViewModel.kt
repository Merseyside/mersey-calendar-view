package com.merseyside.calendar.core.rangeViews.monthView.model

import com.merseyside.adapters.core.feature.selecting.SelectState
import com.merseyside.calendar.core.dayViews.numberView.model.NumberDayViewModel
import com.merseyside.merseyLib.time.ranges.TimeRange

open class MonthDayViewModel(
    item: TimeRange,
    var isOutMonthDay: Boolean = false
) : NumberDayViewModel(item) {

    override val selectState = SelectState(selectable = !isOutMonthDay)
}