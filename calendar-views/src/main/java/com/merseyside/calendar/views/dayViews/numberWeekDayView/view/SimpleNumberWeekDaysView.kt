package com.merseyside.calendar.views.dayViews.numberWeekDayView.view

import android.content.Context
import android.util.AttributeSet
import com.merseyside.calendar.core.rangeViews.weekView.WeekView
import com.merseyside.calendar.core.recycler.timeUnitRecyclerView.TimeUnitRecyclerView
import com.merseyside.calendar.views.R
import com.merseyside.calendar.views.databinding.ViewNumberWeekDayRecyclerBinding
import com.merseyside.calendar.views.dayViews.numberWeekDayView.adapter.NumberWeekDayAdapter
import com.merseyside.calendar.views.dayViews.numberWeekDayView.model.NumberWeekDayViewModel
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.int
import com.merseyside.utils.delegate.viewBinding
import com.merseyside.utils.delegate.getValue

class SimpleNumberWeekDaysWeekView(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : WeekView<NumberWeekDayViewModel>(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attributeSet: AttributeSet): this(context, attributeSet, R.attr.simpleNumberWeekDaysWeekViewStyle)

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.SimpleNumberWeekDaysWeekView,
        "SimpleNumberWeekDaysWeekView",
        defStyleAttr,
        0,
        "number"
    )

    private val weekDayLength by attrs.int()

    private val pattern: String by lazy {
        "E".repeat(weekDayLength)
    }

    private val binding: ViewNumberWeekDayRecyclerBinding by viewBinding(R.layout.view_number_week_day_recycler)
    override val recycler: TimeUnitRecyclerView
        get() = binding.recycler


    override fun provideAdapter(timeRange: WeekRange): NumberWeekDayAdapter {
        return NumberWeekDayAdapter(pattern) {
            adapterConfigure()
        }
    }
}