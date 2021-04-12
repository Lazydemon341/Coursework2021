package com.avvlas.coursework2021.ui.addmacro.pages.triggers

import android.os.Bundle
import android.view.View
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacro.pages.BasePageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TriggersFragment : BasePageFragment(R.layout.fragment_triggers) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TriggersFragment()
    }
}