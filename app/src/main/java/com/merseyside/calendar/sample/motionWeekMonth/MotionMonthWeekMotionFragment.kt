package com.merseyside.calendar.sample.motionWeekMonth

import android.content.Context
import android.os.Bundle
import android.view.View
import com.merseyside.archy.presentation.fragment.BaseBindingFragment
import com.merseyside.calendar.core.sample.R
import com.merseyside.calendar.core.sample.databinding.FragmentMotionBinding
import com.merseyside.merseyLib.time.Time

class MotionMonthWeekMotionFragment : BaseBindingFragment<FragmentMotionBinding>() {
    override fun getLayoutId() = R.layout.fragment_motion

    override fun getTitle(context: Context) = "Week month motion"

    override fun performInjection(bundle: Bundle?, vararg params: Any) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val time = Time.nowGMT

        with(requireBinding()) {
            //motionLayout.setProgress(0.5f)
            weekView.setTime(time)
            monthView.setTime(time)
        }
    }

}