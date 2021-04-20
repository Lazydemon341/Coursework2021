package com.avvlas.coursework2021.ui.addmacro.pages.triggers

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.options.Category
import com.avvlas.coursework2021.domain.model.options.triggers.*
import com.avvlas.coursework2021.ui.addmacro.pages.BaseOptionsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TriggersFragment : BaseOptionsFragment<Trigger>(R.layout.fragment_options_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(items)
    }

    override fun onOptionClick(option: Trigger) {
//        when (option) {
//            is DateTimeTrigger -> MaterialDialog(viewPagerFragment.requireContext()).show {
//                dateTimePicker(
//                    show24HoursView = true,
//                    requireFutureDateTime = true
//                ) { _, dateTime ->
//                    option.timeInMillis = dateTime.timeInMillis
//                    viewModel.macro.triggers.add(option)
//                }
//            }
//        }
        option.onClick(requireActivity(), viewModel.macro)
    }

    companion object {
        const val TITLE = "Triggers"

        fun newInstance() =
            TriggersFragment()
    }

    private val items =
        arrayListOf(
            Category<Trigger>(
                R.drawable.ic_baseline_watch_24, "Category1", arrayListOf(
                    DayTimeTrigger(),
                    DateTimeTrigger(),
                    BluetoothStateChangeTrigger()
                )
            ),
            Category<Trigger>(
                R.drawable.ic_baseline_watch_24, "Category2", arrayListOf(
                    LocationTrigger(R.drawable.ic_baseline_watch_24, "trigger1"),
                    LocationTrigger(R.drawable.ic_baseline_check_24, "trigger2")
                )
            ),
            Category<Trigger>(R.drawable.ic_baseline_watch_24, "Category3", arrayListOf())
        )
}