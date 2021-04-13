package com.avvlas.coursework2021.ui.macroslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacro.AddMacroFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MacrosListFragment : Fragment(R.layout.fragment_macros_list) {

    private val viewModel: MacrosListViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = TITLE

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        view.findViewById<FloatingActionButton>(R.id.add_macro_fab).setOnClickListener {
            navController.navigate(R.id.action_macrosListFragment_to_addMacroFragment)
        }
    }

    companion object {
        private const val TITLE = "Macros"

        @JvmStatic
        fun newInstance() = MacrosListFragment()
    }
}