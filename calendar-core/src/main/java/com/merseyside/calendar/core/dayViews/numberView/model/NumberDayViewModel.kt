package com.merseyside.calendar.core.dayViews.numberView.model

import androidx.databinding.Bindable
import com.merseyside.adapters.core.feature.selecting.SelectState
import com.merseyside.adapters.core.feature.selecting.SelectableItem
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.time.ext.isWeekendDay
import com.merseyside.merseyLib.time.ext.toDayOfMonth
import com.merseyside.merseyLib.time.ext.toDayOfWeek
import com.merseyside.merseyLib.time.ranges.TimeRange

open class NumberDayViewModel(item: TimeRange) : TimeRangeViewModel(item), SelectableItem {

    override val selectState: SelectState = SelectState()

    @get:Bindable
    val dayNumber: Int = item.start.toDayOfMonth().intValue

    final override val isWeekend: Boolean = item.start.toDayOfWeek().isWeekendDay()
}