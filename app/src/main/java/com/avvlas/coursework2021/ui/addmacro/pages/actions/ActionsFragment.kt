package com.avvlas.coursework2021.ui.addmacro.pages.actions

import android.os.Bundle
import android.view.View
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.options.Category
import com.avvlas.coursework2021.domain.model.options.actions.Action
import com.avvlas.coursework2021.domain.model.options.triggers.Trigger
import com.avvlas.coursework2021.ui.addmacro.pages.BasePageFragment
import com.avvlas.coursework2021.ui.addmacro.pages.triggers.TriggersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ActionsFragment : BasePageFragment<Action>(R.layout.fragment_options_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(items)
    }

    companion object {
        const val TITLE = "Actions"

        @JvmStatic
        fun newInstance() =
            ActionsFragment()

        private val items =
            arrayListOf(
                Category<Action>(R.drawable.ic_baseline_watch_24, "Category1", arrayListOf()),
                Category<Action>(R.drawable.ic_baseline_watch_24, "Category1", arrayListOf()),
                Category<Action>(R.drawable.ic_baseline_watch_24, "Category1", arrayListOf())
            )
    }
}