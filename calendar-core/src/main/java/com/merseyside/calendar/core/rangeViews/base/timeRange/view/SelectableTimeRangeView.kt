package com.merseyside.calendar.core.rangeViews.base.timeRange.view

import android.content.Context
import android.util.AttributeSet
import com.merseyside.adapters.core.config.AdapterConfig
import com.merseyside.adapters.core.config.ext.getAdapterSelect
import com.merseyside.adapters.core.feature.selecting.AdapterSelect
import com.merseyside.adapters.core.feature.selecting.Selecting
import com.merseyside.calendar.core.R
import com.merseyside.calendar.core.rangeViews.base.timeRange.model.TimeRangeViewModel
import com.merseyside.merseyLib.kotlin.utils.safeLet
import com.merseyside.merseyLib.time.ext.logHuman
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.utils.attributes.AttributeHelper
import com.merseyside.utils.delegate.bool
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
        "SelectableTimeRangeView",
        defStyleAttr,
        0,
        "time"
    )

    private var onSelectCallback: (TimeRange, Boolean, Boolean) -> Unit = { range, _, _ ->
        range.logHuman(prefix = "Time unit selected but callback not set.")
    }

    var selectable by attrs.bool { _, newValue ->
        adapterSelect?.isSelectEnabled = newValue
    }

    var cancellableSelection by attrs.bool()

    open val adapterSelect: AdapterSelect<TimeRange, out TimeRangeViewModel>?
        get() = timeRangeAdapterProvider.currentAdapter?.adapterConfig?.getAdapterSelect()

    override suspend fun onItemsAdded() {
        safeLet(adapterSelect) { select ->
            //select.selectFirstIf(predicate = { model -> model.item.contains(time) })
        }
    }

    open fun getModelClass(): Class<*> {
        return ReflectionUtils.getGenericParameterClass(
            this.javaClass,
            SelectableTimeRangeView::class.java,
            1
        )
    }

    fun AdapterConfig<TimeRange, Model>.adapterConfigure() {

        Selecting {
            isAllowToCancelSelection = cancellableSelection
            isSelectEnabled = selectable
            onSelect = { range, isSelected, isSelectedByUser ->
                onSelectCallback(
                    range,
                    isSelected,
                    isSelectedByUser
                )
            }
        }
    }

    fun onSelect(block: (TimeRange, Boolean, Boolean) -> Unit) {
        onSelectCallback = block
    }
}