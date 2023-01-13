package com.merseyside.calendar.simpleView.adapter

import com.merseyside.adapters.SimpleAdapter
import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.init.initAdapter
import com.merseyside.calendar.R
import com.merseyside.calendar.BR
import com.merseyside.calendar.model.SimpleDayViewModel
import com.merseyside.merseyLib.time.ranges.TimeRange

open class SimpleDayAdapter<VM : SimpleDayViewModel>(
    config: AdapterConfig<TimeRange, VM>
) : SimpleAdapter<TimeRange, VM>(config) {

    override fun getLayoutIdForPosition(position: Int) = R.layout.view_simple_day
    override fun getBindingVariable() = BR.model
    override fun createItemViewModel(item: TimeRange): VM = SimpleDayViewModel(item) as VM

    companion object {
        operator fun invoke(
            configure: AdapterConfig<TimeRange, SimpleDayViewModel>.() -> Unit
        ): SimpleDayAdapter<SimpleDayViewModel> {
            return initAdapter(::SimpleDayAdapter, configure)
        }
    }
}