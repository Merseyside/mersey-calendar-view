package com.merseyside.calendar.views.rangeViews.weekView

import android.content.Context
import android.util.AttributeSet
import com.merseyside.calendar.core.dayViews.numberView.model.NumberDayViewModel
import com.merseyside.calendar.core.rangeViews.base.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.rangeViews.weekView.WeekView
import com.merseyside.calendar.core.rangeViews.weekView.adapter.WeekDayAdapter
import com.merseyside.calendar.core.rangeViews.weekView.recycler.WeekRecyclerView
import com.merseyside.calendar.views.R
import com.merseyside.calendar.views.databinding.ViewWeekTimeUnitBinding
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.utils.delegate.getValue
import com.merseyside.utils.delegate.viewBinding

/**
 * Your custom attrs have to be inherited from WeekView
 */
class SimpleWeekView(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
    WeekView<NumberDayViewModel>(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        R.attr.simpleWeekViewStyle
    )

    private val binding: ViewWeekTimeUnitBinding by viewBinding(R.layout.view_week_time_unit)
    override val recycler: WeekRecyclerView
        get() = binding.recyclerWeek


    override fun provideAdapter(timeRange: WeekRange): TimeRangeAdapter<NumberDayViewModel> {
        return WeekDayAdapter {
            adapterConfigure()
        }
    }
}