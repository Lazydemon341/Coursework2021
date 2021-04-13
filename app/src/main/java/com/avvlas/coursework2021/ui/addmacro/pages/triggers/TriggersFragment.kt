package com.avvlas.coursework2021.ui.addmacro.pages.triggers

import android.os.Bundle
import android.view.View
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.options.Category
import com.avvlas.coursework2021.domain.model.options.triggers.DateTrigger
import com.avvlas.coursework2021.domain.model.options.triggers.Trigger
import com.avvlas.coursework2021.ui.addmacro.pages.BasePageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TriggersFragment : BasePageFragment<Trigger>(R.layout.fragment_options_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(items)
    }

    companion object {
        const val TITLE = "Triggers"

        @JvmStatic
        fun newInstance() =
            TriggersFragment()

        private val items =
            arrayListOf(
                Category<Trigger>(
                    R.drawable.ic_baseline_watch_24, "Category1", arrayListOf(
                        DateTrigger(R.drawable.ic_baseline_watch_24, "trigger1"),
                        DateTrigger(R.drawable.ic_baseline_check_24, "trigger2")
                    )
                ),
                Category<Trigger>(
                    R.drawable.ic_baseline_watch_24, "Category2", arrayListOf(
                        DateTrigger(R.drawable.ic_baseline_watch_24, "trigger1"),
                        DateTrigger(R.drawable.ic_baseline_check_24, "trigger2")
                    )
                ),
                Category<Trigger>(R.drawable.ic_baseline_watch_24, "Category3", arrayListOf())
            )
    }
}