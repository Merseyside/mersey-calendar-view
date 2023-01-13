package com.merseyside.calendar.simpleView.view

import androidx.databinding.BindingAdapter
import com.merseyside.calendar.model.SimpleDayViewModel

@BindingAdapter("dayModel")
fun bindModel(view: SimpleDayView, model: SimpleDayViewModel?) {
    if (model != null) {
        view.applyModel(model)
    }
}

