package com.avvlas.coursework2021.ui.addmacro.pages.actions

import android.os.Bundle
import android.view.View
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacro.pages.BasePageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ActionsFragment : BasePageFragment(R.layout.fragment_actions) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ActionsFragment()
    }
}