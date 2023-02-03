package com.merseyside.calendar.core.dayViews.numberView.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.core.graphics.toRectF
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.dayViews.numberView.model.NumberDayViewModel
import com.merseyside.calendar.core.dayViews.base.DayItemView
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.int
import com.merseyside.utils.view.canvas.ext.drawRoundRectPreventClip
import com.merseyside.utils.view.canvas.ext.drawTextCenter

open class NumberDayView(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : DayItemView(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context,
        attrs,
        R.attr.numberDayViewStyle
    )

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


    override fun Canvas.drawContent(rect: Rect, numberPaint: Paint) {
        drawTextCenter(rect, numberPaint, dayNumber.toString())
    }

    override fun Canvas.drawForeground() {}

    override fun Canvas.drawBackgroundRect(rect: Rect, cornerRadius: Float, paint: Paint) {
        drawRoundRectPreventClip(rect.toRectF(), cornerRadius, cornerRadius, paint)
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