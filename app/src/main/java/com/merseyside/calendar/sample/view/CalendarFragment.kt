package com.merseyside.calendar.sample.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.merseyside.archy.presentation.fragment.BaseBindingFragment
import com.merseyside.calendar.sample.R
import com.merseyside.calendar.sample.databinding.FragmentCalendarBinding
import com.merseyside.merseyLib.time.Time

class CalendarFragment : BaseBindingFragment<FragmentCalendarBinding>() {

    override fun getLayoutId() = R.layout.fragment_calendar
    override fun performInjection(bundle: Bundle?, vararg params: Any) {}
    override fun getTitle(context: Context) = "Calendar"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val time = Time.nowGMT

        with(requireBinding()) {
//            weekView.setTime(time)
            monthView.setTime(time)
        }
    }
}