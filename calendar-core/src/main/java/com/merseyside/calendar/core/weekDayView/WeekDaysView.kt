package com.merseyside.calendar.core.weekDayView

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.timeRange.view.TimeRangeView
import com.merseyside.calendar.core.databinding.ViewWeekDayTimeUnitBinding
import com.merseyside.calendar.core.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.weekDayView.adapter.WeekdayAdapter
import com.merseyside.calendar.core.weekDayView.model.WeekdayViewModel
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.ext.toWeekRange
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.getValue
import com.merseyside.utils.delegate.int
import com.merseyside.utils.delegate.viewBinding
import com.merseyside.utils.layout.AutofitLinearLayoutManager
import com.merseyside.utils.layout.LinearLayoutManager

open class WeekDaysView(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) :
    TimeRangeView<WeekRange, WeekdayViewModel>(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attributeSet: AttributeSet) : this(
        context,
        attributeSet,
        R.attr.weekdaysViewStyle
    )

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.WeekDaysView,
        "WeekDaysView",
        defStyleAttr,
        0,
        "weekday"
    )

    private val weekdayLength by attrs.int(resName = "length")

    private val pattern: String by lazy {
        "E".repeat(weekdayLength)
    }

    val binding: ViewWeekDayTimeUnitBinding by viewBinding(R.layout.view_week_day_time_unit)
    override val recycler by lazy { binding.recyclerWeekDay }

    private val weekdayAdapter: WeekdayAdapter by lazy { WeekdayAdapter(pattern) }

    override fun getTimeRangeAdapter(timeRange: WeekRange): TimeRangeAdapter<out TimeRangeViewModel> {
        return weekdayAdapter
    }

    override suspend fun onItemsAdded() {}

    init {
        setTime(Time.nowGMT)
    }

    override fun getTimeRange(time: TimeUnit): WeekRange {
        return time.toWeekRange()
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return AutofitLinearLayoutManager(context, orientation = HORIZONTAL)
    }
}