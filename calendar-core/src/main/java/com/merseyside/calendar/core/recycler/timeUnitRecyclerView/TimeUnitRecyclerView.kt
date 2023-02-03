package com.merseyside.calendar.core.recycler.timeUnitRecyclerView

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

open class TimeUnitRecyclerView(context: Context, attrs: AttributeSet, defStyle: Int) :
    RecyclerView(context, attrs, defStyle) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
}