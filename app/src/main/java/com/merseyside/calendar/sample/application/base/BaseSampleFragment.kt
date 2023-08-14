package com.merseyside.calendar.sample.application.base

import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.fragment.BaseBindingFragment
import com.merseyside.archy.utils.ext.navigateUp

abstract class BaseSampleFragment<V: ViewDataBinding> : BaseBindingFragment<V>() {

    override fun isNavigateUpEnabled(): Boolean {
        return true
    }

    override fun onNavigateUp() {
        navigateUp()
    }
}