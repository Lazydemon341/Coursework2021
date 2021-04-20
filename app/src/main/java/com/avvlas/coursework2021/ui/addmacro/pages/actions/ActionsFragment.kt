package com.avvlas.coursework2021.ui.addmacro.pages.actions

import android.os.Bundle
import android.view.View
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.options.Category
import com.avvlas.coursework2021.domain.model.options.actions.Action
import com.avvlas.coursework2021.domain.model.options.actions.ChangeAutoRotateAction
import com.avvlas.coursework2021.domain.model.options.actions.ChangeBluetoothStateAction
import com.avvlas.coursework2021.domain.model.options.actions.ChangeWifiStateAction
import com.avvlas.coursework2021.ui.addmacro.pages.BaseOptionsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ActionsFragment : BaseOptionsFragment<Action>(R.layout.fragment_options_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(items)
    }

    override fun onOptionClick(option: Action) {
        option.onClick(requireActivity(), viewModel.macro)
    }

    companion object {
        const val TITLE = "Actions"

        fun newInstance() =
            ActionsFragment()
    }

    private val items =
        arrayListOf(
            Category<Action>(
                R.drawable.ic_baseline_circle_notifications_24,
                "Notifications",
                arrayListOf(
                    ChangeAutoRotateAction(),
                    ChangeWifiStateAction(),
                    ChangeBluetoothStateAction()
                )
            ),
            Category<Action>(R.drawable.ic_baseline_watch_24, "Category1", arrayListOf()),
            Category<Action>(R.drawable.ic_baseline_watch_24, "Category1", arrayListOf())
        )
}