package com.avvlas.coursework2021.ui.addmacro.pages.triggers

import android.os.Bundle
import android.view.View
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.options.Category
import com.avvlas.coursework2021.domain.model.options.triggers.*
import com.avvlas.coursework2021.ui.addmacro.pages.BaseOptionsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TriggersFragment : BaseOptionsFragment<Trigger>(R.layout.fragment_options_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(viewModel.triggers)
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
}