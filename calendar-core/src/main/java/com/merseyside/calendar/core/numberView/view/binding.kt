package com.merseyside.calendar.core.numberView.view

import androidx.databinding.BindingAdapter
import com.merseyside.calendar.core.numberView.model.NumberDayViewModel
import com.merseyside.merseyLib.kotlin.logger.log

@BindingAdapter("numberDayModel")
fun bindModel(view: NumberDayView, model: NumberDayViewModel?) {
    if (model != null) {
        view.applyModel(model)
    }
}

