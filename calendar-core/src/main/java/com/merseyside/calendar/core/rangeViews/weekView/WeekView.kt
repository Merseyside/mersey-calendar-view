package com.merseyside.calendar.core.rangeViews.weekView

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.calendar.core.dayViews.numberView.model.NumberDayViewModel
import com.merseyside.calendar.core.rangeViews.base.timeRange.view.SelectableTimeRangeView
import com.merseyside.merseyLib.time.ext.getNextWeek
import com.merseyside.merseyLib.time.ext.getPrevWeek
import com.merseyside.merseyLib.time.ext.toWeekRange
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.utils.layout.AutofitLinearLayoutManager

abstract class WeekView<ViewModel : NumberDayViewModel>(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int
) : SelectableTimeRangeView<WeekRange, ViewModel>(context, attrs, defStyleAttr) {

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return AutofitLinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    override fun getNextRange(timeRange: WeekRange): WeekRange {
        return timeRange.getNextWeek()
    }

    override fun getPrevRange(timeRange: WeekRange): WeekRange {
        return timeRange.getPrevWeek()
    }

    final override fun getTimeRange(time: TimeUnit): WeekRange {
        return time.toWeekRange()
    }
}