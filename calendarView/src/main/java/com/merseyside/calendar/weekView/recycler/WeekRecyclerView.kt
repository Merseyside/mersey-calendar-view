package com.merseyside.calendar.weekView.recycler

import android.content.Context
import android.util.AttributeSet
import com.merseyside.calendar.timeUnitRecyclerView.TimeUnitRecyclerView

class WeekRecyclerView(context: Context, attrs: AttributeSet, defStyle: Int) : TimeUnitRecyclerView(
    context, attrs, defStyle
) {

    constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

}