package com.merseyside.calendar.core.rangeViews.base.timeRange.model

import androidx.databinding.Bindable
import com.merseyside.adapters.core.model.AdapterParentViewModel
import com.merseyside.adapters.core.model.AdapterViewModel
import com.merseyside.adapters.core.model.VM
import com.merseyside.calendar.core.BR
import com.merseyside.merseyLib.time.ranges.TimeRange

abstract class TimeRangeViewModel(
    item: TimeRange
) : AdapterViewModel<TimeRange>(item) {

    @get:Bindable
    open var isVisible: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                notifyUpdate()
            }
        }

    abstract val isWeekend: Boolean

    override fun areItemsTheSame(other: TimeRange): Boolean {
        return item.start == other.start
    }

    override fun notifyUpdate() {
        super.notifyUpdate()
        notifyPropertyChanged(BR.visible)
    }
}