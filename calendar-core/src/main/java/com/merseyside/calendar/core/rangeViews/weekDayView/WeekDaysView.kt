package com.merseyside.calendar.core.rangeViews.weekDayView

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.rangeViews.base.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.calendar.core.rangeViews.base.timeRange.view.TimeRangeView
import com.merseyside.calendar.core.rangeViews.weekDayView.adapter.WeekdayAdapter
import com.merseyside.calendar.core.rangeViews.weekDayView.model.WeekdayViewModel
import com.merseyside.merseyLib.time.ext.toWeekRange
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.attributes.int
import com.merseyside.utils.layout.AutofitLinearLayoutManager

/**
 * Your custom attrs have to be inherited from WeekView
 * weekdaysViewStyle attr by default
 */
abstract class WeekDaysView<ViewModel : WeekdayViewModel>(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : TimeRangeView<WeekRange, ViewModel>(context, attributeSet, defStyleAttr) {

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.WeekDaysView,
        defStyleAttr,
        0
    )

    private val weekdayLength by attrs.int(R.styleable.WeekDaysView_weekdayLength)

    private val pattern: String by lazy {
        "E".repeat(weekdayLength)
    }

    abstract fun getTimeRangeAdapter(timeRange: WeekRange, pattern: String): WeekdayAdapter

    final override fun getTimeRangeAdapter(timeRange: WeekRange): TimeRangeAdapter<ViewModel> {
        return getTimeRangeAdapter(timeRange, pattern) as TimeRangeAdapter<ViewModel>
    }

    final override fun getTimeRange(time: TimeUnit): WeekRange {
        return time.toWeekRange()
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return AutofitLinearLayoutManager(context, orientation = HORIZONTAL)
    }
}