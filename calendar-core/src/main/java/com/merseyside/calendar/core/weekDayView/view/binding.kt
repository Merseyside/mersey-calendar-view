package com.merseyside.calendar.core.weekDayView.view

import androidx.databinding.BindingAdapter
import com.merseyside.calendar.core.weekDayView.model.WeekdayViewModel

@BindingAdapter("weekdayModel")
fun setWeekdayModel(view: WeekdayView, model: WeekdayViewModel?) {
    if (model != null) {
        view.applyModel(model)
    }
}