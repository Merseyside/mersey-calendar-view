package com.merseyside.calendar.core.rangeViews.base.timeRange.view

import android.content.Context
import android.util.AttributeSet
import com.merseyside.calendar.core.rangeViews.base.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.rangeViews.base.timeRange.provider.TimeRangeAdapterProvider
import com.merseyside.merseyLib.time.ranges.TimeRange

abstract class MutableTimeRangeView<TR : TimeRange, Model : TimeRangeViewModel>(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : TimeRangeView<TR, Model>(context, attributeSet, defStyleAttr) {

    private val timeRangeAdapterProvider =
        TimeRangeAdapterProvider<TR, TimeRangeAdapter<Model>, Model>()

    abstract fun provideAdapter(timeRange: TR): TimeRangeAdapter<Model>

    init {
        timeRangeAdapterProvider.setProviderCallback(object :
            TimeRangeAdapterProvider.ProviderCallback<TR, TimeRangeAdapter<Model>> {
            override fun createAdapterWithTimeRange(timeRange: TR): TimeRangeAdapter<Model> {
                return provideAdapter(timeRange)
            }
        })
    }

    protected abstract fun getNextRange(timeRange: TR): TR

    protected abstract fun getPrevRange(timeRange: TR): TR

    final override fun getTimeRangeAdapter(timeRange: TR): TimeRangeAdapter<Model> {
        return timeRangeAdapterProvider.getTimeRangeAdapter(timeRange)
    }

    final override fun onTimeRangeUpdated(timeRange: TR) {
        super.onTimeRangeUpdated(timeRange)
        onTimeRangeUpdated(timeRange, timeRangeAdapterProvider.getTimeRangeAdapter(timeRange))
    }

    open fun onTimeRangeUpdated(
        timeRange: TimeRange,
        adapter: TimeRangeAdapter<out TimeRangeViewModel>
    ) {}

    /**
     * Calculates and shows next
     */
    fun next(onComplete: (Unit) -> Unit = {}) {
        timeRange = getNextRange(timeRange)
        invalidateTimeRange(timeRange, onComplete = onComplete)
    }

    fun previous(onComplete: (Unit) -> Unit = {}) {
        timeRange = getPrevRange(timeRange)
        invalidateTimeRange(timeRange, onComplete = onComplete)
    }
}