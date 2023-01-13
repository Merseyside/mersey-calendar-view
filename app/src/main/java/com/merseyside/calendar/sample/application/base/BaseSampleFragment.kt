package com.merseyside.calendar.sample.application.base

import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.fragment.BaseBindingFragment
import com.merseyside.archy.presentation.model.BaseViewModel
import com.merseyside.archy.presentation.view.OnBackPressedListener
import com.merseyside.calendar.sample.application.SampleApplication

abstract class BaseSampleFragment<V: ViewDataBinding> : BaseBindingFragment<V>(),
    OnBackPressedListener {

    abstract fun hasTitleBackButton(): Boolean

    override fun onStart() {
        super.onStart()
        setTitleBackButtonEnabled()
    }

    private fun setTitleBackButtonEnabled() {
        if (getActionBar() != null) {
            getActionBar()!!.setDisplayHomeAsUpEnabled(hasTitleBackButton())

            if (hasTitleBackButton()) {
                setHasOptionsMenu(true)
            }
        }
    }

    @CallSuper
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            goBack()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}