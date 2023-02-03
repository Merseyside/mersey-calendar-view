package com.merseyside.calendar.views.dayViews.numberWeekDayView.view

import androidx.databinding.BindingAdapter
import com.merseyside.calendar.views.dayViews.numberWeekDayView.model.NumberWeekDayViewModel

@BindingAdapter("numberWeekDayModel")
fun applyModel(view: NumberWeekDayView, model: NumberWeekDayViewModel?) {
    if (model != null) {
        view.applyModel(model)
    }
}