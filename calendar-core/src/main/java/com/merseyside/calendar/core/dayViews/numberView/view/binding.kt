package com.merseyside.calendar.core.dayViews.numberView.view

import androidx.databinding.BindingAdapter
import com.merseyside.calendar.core.dayViews.numberView.model.NumberDayViewModel

@BindingAdapter("numberDayModel")
fun bindModel(view: NumberDayView, model: NumberDayViewModel?) {
    if (model != null) {
        view.applyModel(model)
    }
}

