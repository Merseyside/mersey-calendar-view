package com.merseyside.calendar.monthView

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.adapters.core.feature.selecting.Selecting
import com.merseyside.calendar.R
import com.merseyside.calendar.databinding.ViewMonthTimeUnitBinding
import com.merseyside.calendar.TimeRangeView
import com.merseyside.calendar.monthView.adapter.MonthDayAdapter
import com.merseyside.merseyLib.time.ext.contains
import com.merseyside.merseyLib.time.ext.supplementToCalendarMonth
import com.merseyside.merseyLib.time.ext.toMonthRange
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.bool
import com.merseyside.utils.delegate.getValue
import com.merseyside.utils.delegate.viewBinding
import com.merseyside.calendar.timeUnitRecyclerView.TimeUnitRecyclerView
import com.merseyside.utils.layout.AutofitGridLayoutManager

class MonthView(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) :
    TimeRangeView<TimeRange>(context, attributeSet, defStyleAttr) {

    private val monthAttrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.MonthView,
        "MonthView",
        defStyleAttr,
        0,
        "month"
    )

    private val showOutMonthDays: Boolean by monthAttrs.bool()

    override val binding: ViewMonthTimeUnitBinding by viewBinding(R.layout.view_month_time_unit)
    override val recycler: TimeUnitRecyclerView
        get() = binding.recyclerMonth

    private lateinit var monthRange: MonthRange

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, R.attr.monthViewStyle)

    private fun isOutMonthDay(range: TimeRange): Boolean {
        return !monthRange.contains(range.start)
    }

    override val unitAdapter = MonthDayAdapter(
        showOutMonthDays,
        ::isOutMonthDay
    ) {
        //coroutineContext = defaultDispatcher
        Selecting()
    }

    init {
        setupRecycler()
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return AutofitGridLayoutManager(context, spanCount = 7, orientation = VERTICAL, reverseLayout = false)
    }

    override fun getTimeRange(time: TimeUnit): TimeRange {
        monthRange = time.toMonthRange()
        return monthRange.supplementToCalendarMonth()
    }
}