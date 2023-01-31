package com.merseyside.calendar.core.monthView

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.databinding.ViewMonthTimeUnitBinding
import com.merseyside.calendar.core.monthView.adapter.MonthDayAdapter
import com.merseyside.calendar.core.monthView.model.MonthDayViewModel
import com.merseyside.calendar.core.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.timeRange.view.SelectableTimeRangeView
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.bool
import com.merseyside.utils.delegate.getValue
import com.merseyside.utils.delegate.viewBinding
import com.merseyside.calendar.core.timeUnitRecyclerView.TimeUnitRecyclerView
import com.merseyside.merseyLib.time.ext.*
import com.merseyside.utils.layout.AutofitGridLayoutManager

open class MonthView(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) :
    SelectableTimeRangeView<MonthRange, MonthDayViewModel>(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, R.attr.monthViewStyle)

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.MonthView,
        "MonthView",
        defStyleAttr,
        0,
        "month"
    )

    override fun provideAdapter(timeRange: MonthRange): TimeRangeAdapter<out TimeRangeViewModel> {
        return MonthDayAdapter(
            timeRange,
            showOutMonthDays,
        ) {
            adapterConfigure()
        }
    }

    private val showOutMonthDays: Boolean by attrs.bool()

    val binding: ViewMonthTimeUnitBinding by viewBinding(R.layout.view_month_time_unit)
    override val recycler: TimeUnitRecyclerView
        get() = binding.recyclerMonth

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return AutofitGridLayoutManager(
            context,
            7,
            RecyclerView.VERTICAL,
            false
        )
    }

    override fun getNextRange(timeRange: MonthRange): MonthRange {
        return timeRange.getNextMonth()
    }

    override fun timeRangeToListOfDayRanges(timeRange: MonthRange): List<TimeRange> {
        return timeRange.supplementToCalendarMonth().toDayRanges()
    }

    override fun getPrevRange(timeRange: MonthRange): MonthRange {
        return timeRange.getPrevMonth()
    }

    override fun getTimeRange(time: TimeUnit): MonthRange {
        return time.toMonthRange()
    }
}