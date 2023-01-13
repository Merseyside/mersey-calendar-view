package com.merseyside.calendar.monthView.view

import androidx.databinding.BindingAdapter
import com.merseyside.calendar.monthView.model.MonthDayViewModel
import com.merseyside.merseyLib.kotlin.utils.safeLet

//@BindingAdapter("monthDayModel")
//fun bindMonthModel(view: MonthDayView, model: MonthDayViewModel?) {
//    safeLet(model) {
//        view.setOutOfMonth(it.isOutMonthDay)
//        view.setDay(it.monthDay)
//    }
//}