package com.merseyside.calendar.core.rangeViews.base.timeRange.view

import android.content.Context
import android.util.AttributeSet
import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.feature.selecting.AdapterSelect
import com.merseyside.adapters.core.feature.selecting.Selecting
import com.merseyside.adapters.core.feature.selecting.config.getAdapterSelect
import com.merseyside.adapters.core.feature.selecting.ext.selectFirstIf
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.rangeViews.base.timeRange.adapter.TimeRangeAdapter
import com.merseyside.calendar.core.rangeViews.base.timeRange.callback.TimeSelectListener
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.kotlin.utils.safeLet
import com.merseyside.merseyLib.time.ext.contains
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.attributes.bool
import com.merseyside.utils.reflection.ReflectionUtils

abstract class SelectableTimeRangeView<TR : TimeRange, Model : TimeRangeViewModel>(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int
) : MutableTimeRangeView<TR, Model>(context, attributeSet, defStyleAttr) {

    private val attrs = AttributeHelper(
        context,
        attributeSet,
        R.styleable.SelectableTimeRangeView,
        defStyleAttr,
        0
    )

    private var cancellableSelection by attrs.bool(R.styleable.SelectableTimeRangeView_timeCancellableSelection)

    private var selectListener: TimeSelectListener? = null

    var selectable by attrs.bool(R.styleable.SelectableTimeRangeView_timeSelectable) { _, newValue ->
        adapterSelect?.isSelectEnabled = newValue
    }

    val selectedRange: TimeRange
        get() = adapterSelect?.getSelectedItem()
            ?: throw IllegalStateException("Not selectable or view not initialized!")

    private var attachedAdapter: TimeRangeAdapter<Model>? = null

    open val adapterSelect: AdapterSelect<TimeRange, out TimeRangeViewModel>?
        get() = attachedAdapter?.adapterConfig?.getAdapterSelect()

    override fun setTime(time: TimeUnit): Boolean {
        return super.setTime(time).also { result ->
            if (!result) {
                selectTime(time)
            }
        }
    }

    override fun onAttachAdapter(
        timeUnit: TimeUnit,
        timeRange: TR,
        adapter: TimeRangeAdapter<Model>
    ) {
        super.onAttachAdapter(timeUnit, timeRange, adapter)
        attachedAdapter = adapter
        selectTime(timeUnit)
    }

    override fun onDetachAdapter(adapter: TimeRangeAdapter<Model>) {
        super.onDetachAdapter(adapter)
        adapter.adapterConfig.getAdapterSelect()?.clear()
    }

    fun AdapterConfig<TimeRange, Model>.adapterConfigure() {

        Selecting {
            isAllowToCancelSelection = cancellableSelection
            isSelectEnabled = selectable
            forceSelect = false
            onSelect = { range, isSelected, isSelectedByUser ->
                selectListener?.onSelect(range, isSelected, isSelectedByUser)
            }
        }
    }

    fun setOnTimeSelectListener(listener: TimeSelectListener) {
        selectListener = listener
    }

    fun onSelect(block: ((TimeRange, Boolean, Boolean) -> Unit)?) {
        selectListener = safeLet(block) {
             object : TimeSelectListener {
                override fun onSelect(
                    timeRange: TimeRange,
                    isSelect: Boolean,
                    isSelectByUser: Boolean
                ) {
                    it.invoke(timeRange, isSelect, isSelectByUser)
                }

            }
        }
    }

    private fun selectTime(timeUnit: TimeUnit) {
        safeLet(adapterSelect) { select ->
            select.selectFirstIf(predicate = { model ->
                model.item.contains(timeUnit)
            })
        }
    }

    protected open fun getModelClass(): Class<*> {
        return ReflectionUtils.getGenericParameterClass(
            this.javaClass,
            SelectableTimeRangeView::class.java,
            1
        )
    }
}