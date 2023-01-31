package com.merseyside.calendar.core.monthView.adapter

import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.monthView.model.MonthDayViewModel
import com.merseyside.calendar.core.monthView.model.OutMonthDayViewModel
import com.merseyside.calendar.core.numberView.adapter.NumberDayAdapter
import com.merseyside.merseyLib.time.ext.contains
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange

class MonthDayAdapter(
    config: AdapterConfig<TimeRange, MonthDayViewModel>,
    private val monthRange: MonthRange,
    private val isPrevMonthDayVisible: Boolean
) : NumberDayAdapter<MonthDayViewModel>(config) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLayoutIdForPosition(position: Int) = R.layout.view_month_day

    override fun createItemViewModel(item: TimeRange): MonthDayViewModel {
        return if (!isOutMonthDay(item)) {
            MonthDayViewModel(item)
        } else {
            OutMonthDayViewModel(item, isPrevMonthDayVisible)
        }
    }

    private fun isOutMonthDay(timeRange: TimeRange): Boolean {
        return !monthRange.contains(timeRange.start)
    }

    companion object {
        operator fun invoke(
            monthRange: MonthRange,
            isPrevMonthDayVisible: Boolean,
            configure: AdapterConfig<TimeRange, MonthDayViewModel>.() -> Unit
        ): MonthDayAdapter {
            return initAdapter(::MonthDayAdapter, monthRange, isPrevMonthDayVisible, configure)
        }
    }
}