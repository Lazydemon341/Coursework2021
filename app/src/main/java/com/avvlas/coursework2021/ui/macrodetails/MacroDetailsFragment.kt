package com.avvlas.coursework2021.ui.macrodetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MacroDetailsFragment : Fragment(R.layout.fragment_macro_details) {

    private val viewModel: MacroDetailsViewModel by viewModels()
    private lateinit var navController: NavController

    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setupStatusBar
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            actionBar = this
            setDisplayHomeAsUpEnabled(true)
        }
        setupNavController()

        val macro = arguments?.getParcelable<Macro>(ARG_MACRO) ?: return
        setupMacroDetails(view, macro)
        setupViewModel()
    }

    private fun setupNavController() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }

    fun onBackPressed() {
        navController.navigateUp()
    }

    private fun setupMacroDetails(view: View, macro: Macro) {
        actionBar.title = macro.name
    }

    private fun setupViewModel() {
        // TODO
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.macro_details_menu, menu)
    }

    companion object {
        private const val TITLE = "MacroDetails"

        private const val ARG_MACRO = "macro"
    }
}