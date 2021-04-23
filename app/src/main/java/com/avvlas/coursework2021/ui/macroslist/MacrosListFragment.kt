package com.avvlas.coursework2021.ui.macroslist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MacrosListFragment : Fragment(R.layout.fragment_macros_list),
    MacrosListAdapter.OnMacroClickListener {

    private val viewModel: MacrosListViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var adapter: MacrosListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStatusBar()
        setupNavController(view)
        setupRecyclerView(view)
        setupViewModel()
    }

    private fun setupStatusBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = TITLE
        }
    }

    private fun setupNavController(view: View) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        view.findViewById<FloatingActionButton>(R.id.add_macro_fab).setOnClickListener {
            navController.navigate(R.id.action_macrosListFragment_to_addMacroFragment)
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.adapter = MacrosListAdapter(this).also {
            this.adapter = it
        }
    }

    private fun setupViewModel() {
        viewModel.macros.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onMacroClick(macro: Macro) {
        val bundle = bundleOf("macro" to macro)
        navController.navigate(R.id.action_macrosListFragment_to_macroDetailsFragment, bundle)
        //macro.runTest(requireContext()) TODO: move this to macroDetailsFragment
    }

    companion object {
        private const val TITLE = "Macros"
    }
}