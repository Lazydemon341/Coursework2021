package com.avvlas.coursework2021.ui.addmacros.triggers

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacros.AddMacrosFragment
import com.avvlas.coursework2021.ui.addmacros.AddMacrosViewModel
import com.avvlas.coursework2021.ui.addmacros.BasePageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TriggersFragment : BasePageFragment(R.layout.fragment_triggers) {

    private val viewModel: AddMacrosViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener{
            viewPagerFragment.increment()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TriggersFragment()
    }
}