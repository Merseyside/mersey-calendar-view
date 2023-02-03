package com.merseyside.calendar.sample.simple.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.merseyside.archy.presentation.fragment.BaseBindingFragment
import com.merseyside.calendar.core.sample.R
import com.merseyside.calendar.core.sample.databinding.FragmentCalendarBinding
import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.ext.logHuman
import com.merseyside.utils.view.ext.onClick

class CalendarFragment : BaseBindingFragment<FragmentCalendarBinding>() {

    override fun getLayoutId() = R.layout.fragment_calendar
    override fun performInjection(bundle: Bundle?, vararg params: Any) {}
    override fun getTitle(context: Context) = "Calendar"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val time = Time.nowGMT

        with(requireBinding()) {
            prevBtn.onClick {
                weekView.previous()
                monthView.previous()
            }
            nextBtn.onClick {
                weekView.next()
                monthView.next()
            }

            weekView.apply {
                setTime(time)
                onSelect { range, _, _ -> range.logHuman("CalandarSelect") }
            }
            monthView.setTime(time)
            numberWeekDayWeekView.setTime(time)
        }
    }
}