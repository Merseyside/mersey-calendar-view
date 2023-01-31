package com.merseyside.calendar.core.weekView

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.databinding.ViewWeekTimeUnitBinding
import com.merseyside.calendar.core.numberView.model.NumberDayViewModel
import com.merseyside.calendar.core.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.timeRange.view.SelectableTimeRangeView
import com.merseyside.calendar.core.weekView.adapter.WeekDayAdapter
import com.merseyside.calendar.core.weekView.recycler.WeekRecyclerView
import com.merseyside.merseyLib.time.ext.getNextWeek
import com.merseyside.merseyLib.time.ext.getPrevWeek
import com.merseyside.merseyLib.time.ext.toWeekRange
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.utils.delegate.getValue
import com.merseyside.utils.delegate.viewBinding
import com.merseyside.utils.layout.AutofitLinearLayoutManager

class WeekView(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
    SelectableTimeRangeView<WeekRange, NumberDayViewModel>(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet): this(context, attrs, R.attr.weekViewStyle)

    val binding: ViewWeekTimeUnitBinding by viewBinding(R.layout.view_week_time_unit)
    override val recycler: WeekRecyclerView
        get() = binding.recyclerWeek

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return AutofitLinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    override fun provideAdapter(timeRange: WeekRange): TimeRangeAdapter<out TimeRangeViewModel> {
        return WeekDayAdapter {
            adapterConfigure()
        }
    }

    override fun getNextRange(timeRange: WeekRange): WeekRange {
        return timeRange.getNextWeek()
    }

    override fun getPrevRange(timeRange: WeekRange): WeekRange {
        return timeRange.getPrevWeek()
    }

    override fun getTimeRange(time: TimeUnit): WeekRange {
        return time.toWeekRange()
    }
}