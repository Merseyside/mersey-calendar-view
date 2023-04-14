package com.merseyside.calendar.core.rangeViews.base.timeRange.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.merseyside.adapters.core.async.doAsync
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.rangeViews.base.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.recycler.timeUnitRecyclerView.TimeUnitRecyclerView
import com.merseyside.merseyLib.time.ext.contains
import com.merseyside.merseyLib.time.ext.toDayRanges
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.utils.attributes.AttributeHelper

abstract class TimeRangeView<TR : TimeRange, Model : TimeRangeViewModel>(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.TimeRangeView,
        defStyleAttr,
        0
    )

    abstract val recycler: TimeUnitRecyclerView

    protected lateinit var timeRange: TR

    open fun setupRecycler() {
        recycler.itemAnimator = null
        recycler.layoutManager = getLayoutManager()
    }

    /**
     * @return true if view updated with time was set, false otherwise
     */
    open fun setTime(time: TimeUnit): Boolean {
        initRecyclerIfNeed()

        return if (!::timeRange.isInitialized || !timeRange.contains(time)) {
            timeRange = getTimeRange(time)
            invalidateTimeRange(timeRange, time)

            onTimeRangeUpdated(timeRange)
            true
        } else false
    }

    open fun onTimeRangeUpdated(timeRange: TR) {}

    protected fun invalidateTimeRange(
        timeRange: TR,
        timeUnit: TimeUnit = timeRange.start,
        onComplete: (Unit) -> Unit = {}
    ) {
        val adapter = getTimeRangeAdapter(timeRange)
        adapter.doAsync(onComplete) {
            if (adapter.isEmpty()) fillAdapter(adapter, timeRange)
            if (recycler.adapter != null) onDetachAdapter(recycler.adapter as TimeRangeAdapter<Model>)
            onAttachAdapter(timeUnit, timeRange, adapter)
        }
    }

    private suspend fun fillAdapter(
        adapter: TimeRangeAdapter<out TimeRangeViewModel>,
        timeRange: TR
    ) {
        with(adapter) {
            add(timeRangeToListOfDayRanges(timeRange))
        }
    }

    abstract fun getTimeRangeAdapter(timeRange: TR): TimeRangeAdapter<Model>

    abstract fun getTimeRange(time: TimeUnit): TR

    abstract fun getLayoutManager(): LayoutManager

    /**
     * Calls when adapter have filled with data and before attached to recycler.
     */
    @CallSuper
    open suspend fun onAttachAdapter(
        timeUnit: TimeUnit,
        timeRange: TR,
        adapter: TimeRangeAdapter<Model>
    ) {
        recycler.swapAdapter(adapter, false)
    }

    open suspend fun onDetachAdapter(adapter: TimeRangeAdapter<Model>) {
        adapter.workManager.cancel()
    }

    protected open fun timeRangeToListOfDayRanges(timeRange: TR): List<TimeRange> {
        return timeRange.toDayRanges()
    }

    private fun initRecyclerIfNeed() {
        if (recycler.adapter == null) setupRecycler()
    }
}