package com.merseyside.calendar.core.rangeViews.monthView

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.rangeViews.base.timeRange.view.SelectableTimeRangeView
import com.merseyside.calendar.core.rangeViews.monthView.adapter.MonthDayAdapter
import com.merseyside.calendar.core.rangeViews.monthView.model.MonthDayViewModel
import com.merseyside.merseyLib.time.ext.*
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.utils.attributes1.AttributeHelper
import com.merseyside.utils.attributes1.bool
import com.merseyside.utils.layout.AutofitGridLayoutManager

/**
 * Your custom attrs have to be inherited from MonthView
 */
abstract class MonthView<ViewModel : MonthDayViewModel>(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : SelectableTimeRangeView<MonthRange, ViewModel>(context, attributeSet, defStyleAttr) {

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.MonthView,
        defStyleAttr,
        0
    )

    protected val showOutMonthDays: Boolean by attrs.bool(R.styleable.MonthView_monthShowOutMonthDays)

    abstract fun provideAdapter(timeRange: MonthRange, showOutMonthDays: Boolean): MonthDayAdapter<ViewModel>

    final override fun provideAdapter(timeRange: MonthRange): MonthDayAdapter<ViewModel> {
        return provideAdapter(timeRange, showOutMonthDays)
    }

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

    override fun getPrevRange(timeRange: MonthRange): MonthRange {
        return timeRange.getPrevMonth()
    }

    final override fun timeRangeToListOfDayRanges(timeRange: MonthRange): List<TimeRange> {
        return timeRange.supplementToCalendarMonth().toDayRanges()
    }

    final override fun getTimeRange(time: TimeUnit): MonthRange {
        return time.toMonthRange()
    }
}