package com.avvlas.coursework2021.ui.macroslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacro.AddMacroFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MacrosListFragment : Fragment(R.layout.fragment_macros_list) {

    private val viewModel: MacrosListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = TITLE

        view.findViewById<FloatingActionButton>(R.id.add_macro_fab).setOnClickListener {
            // TODO: navigate
        }
    }

    companion object {
        private const val TITLE = "Macros"

        @JvmStatic
        fun newInstance() = MacrosListFragment()
    }
}