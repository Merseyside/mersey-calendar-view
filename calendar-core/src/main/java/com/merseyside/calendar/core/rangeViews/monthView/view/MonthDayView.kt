package com.merseyside.calendar.core.rangeViews.monthView.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.dayViews.numberView.model.NumberDayViewModel
import com.merseyside.calendar.core.dayViews.numberView.view.NumberDayView
import com.merseyside.calendar.core.rangeViews.monthView.model.MonthDayViewModel
import com.merseyside.merseyLib.kotlin.utils.safeLet
import com.merseyside.utils.attributes1.AttributeHelper
import com.merseyside.utils.attributes1.bool
import com.merseyside.utils.attributes1.colorStateListOrNull

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
        defStyleAttr,
        0
    )

    private var isOutMonthDay by attrs.bool(R.styleable.MonthDayView_monthIsOutMonthDay)
    private val outDayTextColor by attrs.colorStateListOrNull(R.styleable.MonthDayView_monthOutDayTextColor)
    private val outDayBackgroundColor by attrs.colorStateListOrNull(R.styleable.MonthDayView_monthOutDayBackgroundColor)

    override val backgroundColor: ColorStateList
        get() {
            if (isOutMonthDay) {
                safeLet(outDayBackgroundColor) {
                    return it
                }
            }

            return super.backgroundColor
        }

    override val textColor: ColorStateList
        get() {
            if (isOutMonthDay) {
                safeLet(outDayTextColor) {
                    return it
                }
            }

            return super.textColor
        }



    fun setOutOfMonth(isOut: Boolean) {
        isOutMonthDay = isOut
    }

    fun applyModel(model: MonthDayViewModel) {
        applyModel(model as NumberDayViewModel)
        setOutOfMonth(model.isOutMonthDay)
    }
}