package com.merseyside.calendar.core.timeRange.view

import android.content.Context
import android.util.AttributeSet
import com.merseyside.adapters.core.async.clearAsync
import com.merseyside.adapters.core.async.doAsync
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.timeRange.provider.TimeRangeAdapterProvider
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.TimeUnit

abstract class MutableTimeRangeView<TR : TimeRange, Model : TimeRangeViewModel>(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : TimeRangeView<TR, Model>(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        R.attr.selectableTimeRangeViewStyle
    )

    protected val timeRangeAdapterProvider =
        TimeRangeAdapterProvider<TR, TimeRangeAdapter<out TimeRangeViewModel>>()

    abstract fun provideAdapter(timeRange: TR): TimeRangeAdapter<out TimeRangeViewModel>

    init {
        timeRangeAdapterProvider.setProviderCallback(object :
            TimeRangeAdapterProvider.ProviderCallback<TR, TimeRangeAdapter<out TimeRangeViewModel>> {
            override fun createAdapterWithTimeRange(timeRange: TR): TimeRangeAdapter<out TimeRangeViewModel> {
                return provideAdapter(timeRange)
            }

            override fun getNextTimeRange(timeRange: TR): TR {
                return getNextRange(timeRange)
            }
        })
    }

    protected abstract fun getNextRange(timeRange: TR): TR

    protected abstract fun getPrevRange(timeRange: TR): TR

    final override fun getTimeRangeAdapter(timeRange: TR): TimeRangeAdapter<out TimeRangeViewModel> {
        return timeRangeAdapterProvider.getTimeRangeAdapter(timeRange)
    }

    /**
     * Calculates and shows next
     */
    fun next() {
        timeRange = getNextRange(timeRange)
        invalidateTimeRange(timeRange)
    }

    fun previous() {
        timeRange = getPrevRange(timeRange)
        invalidateTimeRange(timeRange)
    }
}