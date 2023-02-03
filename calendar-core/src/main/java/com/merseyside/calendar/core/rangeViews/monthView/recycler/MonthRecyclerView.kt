package com.merseyside.calendar.core.rangeViews.monthView.recycler

import android.content.Context
import android.util.AttributeSet
import com.merseyside.calendar.core.recycler.timeUnitRecyclerView.TimeUnitRecyclerView

class MonthRecyclerView(context: Context, attrs: AttributeSet, defStyle: Int) : TimeUnitRecyclerView(
    context, attrs, defStyle
) {

    constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

}