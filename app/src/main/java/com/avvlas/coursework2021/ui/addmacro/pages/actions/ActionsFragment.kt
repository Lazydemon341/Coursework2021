package com.avvlas.coursework2021.ui.addmacro.pages.actions

import android.os.Bundle
import android.view.View
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.options.actions.Action
import com.avvlas.coursework2021.ui.addmacro.pages.BaseOptionsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ActionsFragment : BaseOptionsFragment<Action>(R.layout.fragment_options_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesAdapter.submitList(viewModel.actions)
        selectedOptionsAdapter.submitList(viewModel.macro.actions)
    }

    override fun onOptionClick(option: Action) {
        option.onClick(requireActivity(), viewModel.macro)
    }

    companion object {
        fun newInstance() =
            ActionsFragment()
    }
}