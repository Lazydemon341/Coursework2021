package com.avvlas.coursework2021.ui.macroslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        setupStatusBar()
        setupNavController(view)
    }

    private fun setupStatusBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = TITLE
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setupNavController(view: View) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        view.findViewById<FloatingActionButton>(R.id.add_macro_fab).setOnClickListener {
            navController.navigate(R.id.action_macrosListFragment_to_addMacroFragment)
        }
    }

    private fun setupViewModel() {
        viewModel.macros.observe(viewLifecycleOwner) {
            // TODO: pass data to listadapter
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TITLE = "Macros"
    }
}