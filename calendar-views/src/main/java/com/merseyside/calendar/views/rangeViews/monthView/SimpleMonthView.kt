package com.merseyside.calendar.views.rangeViews.monthView

import android.content.Context
import android.util.AttributeSet
import com.merseyside.calendar.core.rangeViews.monthView.MonthView
import com.merseyside.calendar.core.rangeViews.monthView.adapter.MonthDayAdapter
import com.merseyside.calendar.core.rangeViews.monthView.model.MonthDayViewModel
import com.merseyside.calendar.core.recycler.timeUnitRecyclerView.TimeUnitRecyclerView
import com.merseyside.calendar.views.R
import com.merseyside.calendar.views.databinding.ViewMonthTimeUnitBinding
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.utils.delegate.getValue
import com.merseyside.utils.delegate.viewBinding

open class SimpleMonthView(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) :
    MonthView<MonthDayViewModel>(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        com.merseyside.calendar.core.R.attr.monthViewStyle
    )

    private val binding: ViewMonthTimeUnitBinding by viewBinding(R.layout.view_month_time_unit)
    override val recycler: TimeUnitRecyclerView
        get() = binding.recyclerMonth

    override fun provideAdapter(timeRange: MonthRange, showOutMonthDays: Boolean): MonthDayAdapter {
        return MonthDayAdapter(
            timeRange,
            showOutMonthDays,
        ) {
            adapterConfigure()
        }
    }
}