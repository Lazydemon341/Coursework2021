package com.avvlas.coursework2021.ui.addmacro.pages.triggers

import android.os.Bundle
import android.view.View
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.options.triggers.Trigger
import com.avvlas.coursework2021.ui.addmacro.pages.BaseOptionsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TriggersFragment : BaseOptionsFragment<Trigger>(R.layout.fragment_options_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriesAdapter.submitList(viewModel.triggers)
        viewModel.macro.triggersLiveData.observe(viewLifecycleOwner) {
            selectedOptionsAdapter.submitList(it.toList())
        }
    }

    companion object {
        fun newInstance() =
            TriggersFragment()
    }
}