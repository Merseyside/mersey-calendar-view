package com.merseyside.calendar.core.numberView.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.core.graphics.toRectF
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.numberView.model.NumberDayViewModel
import com.merseyside.calendar.core.timeRange.view.DayItemView
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.int
import com.merseyside.utils.view.ext.getTextHeight
import com.merseyside.utils.view.ext.getTextWidth
import com.merseyside.utils.view.measure.logMeasureSpec

open class NumberDayView(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : DayItemView(context, attributeSet, defStyleAttr) {

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.NumberDayView,
        "NumberDayView",
        defStyleAttr,
        0,
        "number"
    )

    protected var dayNumber by attrs.int(defaultValue = 0, resName = "day")

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        R.attr.numberDayViewStyle
    )

    override fun getTextContent(): String {
        return dayNumber.toString()
    }

    override fun Canvas.drawForeground() {}

    override fun Canvas.drawBackgroundRect(rect: Rect, paint: Paint) {
        drawRoundRect(rect.toRectF(), cornerRadius.toFloat(), cornerRadius.toFloat(), paint)
    }

    open fun applyModel(model: NumberDayViewModel) {
        isWeekend = model.isWeekend
        dayNumber = model.dayNumber
        invalidate()
    }

    override fun isValid(): Boolean {
        return dayNumber in 1..31
    }
}