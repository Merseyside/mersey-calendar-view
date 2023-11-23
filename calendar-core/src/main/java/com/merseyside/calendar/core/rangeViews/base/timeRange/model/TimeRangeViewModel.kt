package com.merseyside.calendar.core.rangeViews.base.timeRange.model

import androidx.databinding.Bindable
import com.merseyside.adapters.core.model.AdapterViewModel
import com.merseyside.calendar.core.BR
import com.merseyside.merseyLib.time.ranges.TimeRange

abstract class TimeRangeViewModel(item: TimeRange) : AdapterViewModel<TimeRange>(item) {

    override val id: Any = item.start

    @get:Bindable
    open var isVisible: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.visible)
            }
        }

    abstract val isWeekend: Boolean

    override fun areItemsTheSame(other: TimeRange): Boolean {
        return item.start == other.start
    }
}