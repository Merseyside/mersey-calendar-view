package com.merseyside.calendar.core.timeRange.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.viewbinding.ViewBinding
import com.merseyside.adapters.core.async.doAsync
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.timeUnitRecyclerView.TimeUnitRecyclerView
import com.merseyside.merseyLib.time.ext.toDayRanges
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.dimensionPixelSize

abstract class TimeRangeView<TR : TimeRange, Model : TimeRangeViewModel>(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.TimeRangeView,
        "TimeRangeView",
        defStyleAttr,
        0,
        "time"
    )

    abstract val recycler: TimeUnitRecyclerView

    protected lateinit var timeRange: TR

    open fun setupRecycler() {
        recycler.itemAnimator = null
        recycler.layoutManager = getLayoutManager()
    }

    /**
     * Presents time with view initialize
     */
    lateinit var time: TimeUnit
        private set

    fun setTime(time: TimeUnit) {
        initRecyclerIfNeed()
        this.time = time

        timeRange = getTimeRange(time)
        invalidateTimeRange(timeRange)
    }

    protected fun invalidateTimeRange(timeRange: TR) {
        val adapter = getTimeRangeAdapter(timeRange)
        adapter.doAsync(onComplete = {
            recycler.swapAdapter(adapter, false)
        }) {
            if (adapter.isEmpty()) fillAdapter(adapter, timeRange)
        }
    }

    private suspend fun fillAdapter(adapter: TimeRangeAdapter<out TimeRangeViewModel>, timeRange: TR) {
        with(adapter) {
            add(timeRangeToListOfDayRanges(timeRange))
            onItemsAdded()
        }
    }

    abstract fun getTimeRangeAdapter(timeRange: TR): TimeRangeAdapter<out TimeRangeViewModel>

    abstract suspend fun onItemsAdded()

    abstract fun getTimeRange(time: TimeUnit): TR

    abstract fun getLayoutManager(): LayoutManager

    protected open fun timeRangeToListOfDayRanges(timeRange: TR): List<TimeRange> {
        return timeRange.toDayRanges()
    }

    private fun initRecyclerIfNeed() {
        if (recycler.adapter == null) setupRecycler()
    }
}