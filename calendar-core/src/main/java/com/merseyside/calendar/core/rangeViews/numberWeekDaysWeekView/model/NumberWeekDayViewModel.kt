package com.merseyside.calendar.core.rangeViews.numberWeekDaysWeekView.model

import com.merseyside.calendar.core.dayViews.numberView.model.NumberDayViewModel
import com.merseyside.merseyLib.time.ext.getHuman
import com.merseyside.merseyLib.time.ext.toDayOfWeek
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.utils.Pattern

open class NumberWeekDayViewModel(item: TimeRange, pattern: String) : NumberDayViewModel(item) {

    val dayOfWeek = item.start.toDayOfWeek()
    val humanDayOfWeek = dayOfWeek.getHuman(Pattern.CUSTOM(pattern)).take(pattern.length)
}