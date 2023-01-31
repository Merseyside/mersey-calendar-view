package com.merseyside.calendar.core.weekDayView.model

import com.merseyside.calendar.core.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.time.ext.getHuman
import com.merseyside.merseyLib.time.ext.isWeekendDay
import com.merseyside.merseyLib.time.ext.toDayOfWeek
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.utils.Pattern

class WeekdayViewModel(item: TimeRange, pattern: String) : TimeRangeViewModel(item) {

    val dayOfWeek = item.start.toDayOfWeek()
    val humanDayOfWeek = dayOfWeek.getHuman(Pattern.CUSTOM(pattern)).take(pattern.length)

    override val isWeekend: Boolean = dayOfWeek.isWeekendDay()

}