package com.merseyside.calendar.core.monthView.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.monthView.model.MonthDayViewModel
import com.merseyside.calendar.core.numberView.view.NumberDayView
import com.merseyside.calendar.core.numberView.model.NumberDayViewModel
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.bool
import com.merseyside.utils.delegate.colorStateList

class MonthDayView(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : NumberDayView(context, attributeSet, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet): this(context, attrs, R.attr.monthDayViewStyle)

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.MonthDayView,
        "MonthDayView",
        defStyleAttr,
        0,
        "month"
    )

    private var isOutMonthDay by attrs.bool()
    private val prevDayTextColor by attrs.colorStateList()
    private val prevDayBackgroundColor by attrs.colorStateList()

    override val backgroundColor: ColorStateList
        get() {
            return if (isOutMonthDay) {
                prevDayBackgroundColor
            } else super.backgroundColor
        }

    override val textColor: ColorStateList
        get() {
            return if (isOutMonthDay) {
                prevDayTextColor
            } else super.textColor
        }



    fun setOutOfMonth(isOut: Boolean) {
        isOutMonthDay = isOut
    }

    fun applyModel(model: MonthDayViewModel) {
        applyModel(model as NumberDayViewModel)
        setOutOfMonth(model.isOutMonthDay)
    }
}