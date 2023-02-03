package com.merseyside.calendar.core.rangeViews.monthView.view

import androidx.databinding.BindingAdapter
import com.merseyside.calendar.core.rangeViews.monthView.model.MonthDayViewModel
import com.merseyside.merseyLib.kotlin.utils.safeLet

@BindingAdapter("monthDayModel")
fun bindMonthModel(view: MonthDayView, model: MonthDayViewModel?) {
    safeLet(model) { view.applyModel(it) }
}