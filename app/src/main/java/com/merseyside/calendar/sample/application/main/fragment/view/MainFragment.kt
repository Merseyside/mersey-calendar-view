package com.merseyside.calendar.sample.application.main.fragment.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.merseyside.archy.utils.ext.navigate
import com.merseyside.calendar.sample.R
import com.merseyside.calendar.sample.application.base.BaseSampleFragment
import com.merseyside.utils.view.ext.onClick
import com.merseyside.calendar.sample.databinding.FragmentMainBinding

class MainFragment : BaseSampleFragment<FragmentMainBinding>() {

    override fun getLayoutId() = R.layout.fragment_main
    override fun getTitle(context: Context): String = "Choose a feature"
    override fun hasTitleBackButton() = false

    override fun performInjection(bundle: Bundle?, vararg params: Any) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireBinding().run {
            calendarButton.onClick { navigate(R.id.action_mainFragment_to_calendarFragment) }
        }
    }
}