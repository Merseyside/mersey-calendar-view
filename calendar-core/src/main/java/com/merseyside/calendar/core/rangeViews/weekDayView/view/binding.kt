package com.merseyside.calendar.core.rangeViews.weekDayView.view

import androidx.databinding.BindingAdapter
import com.merseyside.calendar.core.rangeViews.weekDayView.model.WeekdayViewModel

@BindingAdapter("weekdayModel")
fun setWeekdayModel(view: WeekdayView, model: WeekdayViewModel?) {
    if (model != null) {
        view.applyModel(model)
    }
}