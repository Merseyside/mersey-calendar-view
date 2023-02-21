package com.merseyside.calendar.views.rangeViews.numberWeekDayView

import android.content.Context
import android.util.AttributeSet
import com.merseyside.calendar.core.dayViews.numberView.adapter.NumberDayAdapter
import com.merseyside.calendar.core.rangeViews.numberWeekDaysWeekView.NumberWeekDaysWeekView
import com.merseyside.calendar.core.rangeViews.numberWeekDaysWeekView.model.NumberWeekDayViewModel
import com.merseyside.calendar.core.recycler.timeUnitRecyclerView.TimeUnitRecyclerView
import com.merseyside.calendar.views.R
import com.merseyside.calendar.views.databinding.ViewNumberWeekDayRecyclerBinding
import com.merseyside.calendar.views.dayViews.numberWeekDayView.adapter.NumberWeekDayAdapter
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.utils.delegate.getValue
import com.merseyside.utils.delegate.viewBinding

class SimpleNumberWeekDaysWeekView(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : NumberWeekDaysWeekView<NumberWeekDayViewModel>(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attributeSet: AttributeSet): this(
        context,
        attributeSet,
        R.attr.simpleNumberWeekDaysWeekViewStyle
    )

    private val binding: ViewNumberWeekDayRecyclerBinding by viewBinding(R.layout.view_number_week_day_recycler)
    override val recycler: TimeUnitRecyclerView
        get() = binding.recycler


    override fun provideAdapter(timeRange: WeekRange): NumberDayAdapter<NumberWeekDayViewModel> {
        return NumberWeekDayAdapter(pattern) {
            adapterConfigure()
        }
    }
}