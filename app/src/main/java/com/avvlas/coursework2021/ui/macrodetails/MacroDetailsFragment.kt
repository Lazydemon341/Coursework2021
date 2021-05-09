package com.avvlas.coursework2021.ui.macrodetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.Option
import com.avvlas.coursework2021.utils.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MacroDetailsFragment : Fragment(R.layout.fragment_macro_details),
    OptionsListAdapter.OnOptionClickListener {

    private lateinit var navController: NavController
    private lateinit var actionBar: ActionBar

    @Inject
    lateinit var viewModelFactory: MacroDetailsViewModel.Factory
    private val viewModel: MacroDetailsViewModel by viewModels {
        MacroDetailsViewModel.provideFactory(viewModelFactory, this, arguments)
    }

    private lateinit var triggersAdapter: OptionsListAdapter
    private lateinit var actionsAdapter: OptionsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if has macro
        arguments?.getParcelable<Macro>(ARG_MACRO)
            ?: throw IllegalArgumentException("Macro required")

        // Setup status bar
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            actionBar = this
            setDisplayHomeAsUpEnabled(true)
        }

        setupNavController()
        setupViews(view)
        setupViewModel()
    }

    private fun setupNavController() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }

    private fun setupViews(view: View) {
        actionBar.title = viewModel.macro.name
        setupRecyclerViews(view)
    }

    private fun setupRecyclerViews(view: View) {
        view.findViewById<RecyclerView>(R.id.triggers_recycler_view).apply {
            adapter = OptionsListAdapter(this@MacroDetailsFragment).also {
                triggersAdapter = it
            }
            addItemDecoration(GridSpacingItemDecoration(1, 8, false))
        }
        view.findViewById<RecyclerView>(R.id.actions_recycler_view).apply {
            adapter = OptionsListAdapter(this@MacroDetailsFragment).also {
                actionsAdapter = it
            }
            addItemDecoration(GridSpacingItemDecoration(1, 8, false))
        }
    }

    private fun setupViewModel() {
        viewModel.macro.triggersLiveData.observe(viewLifecycleOwner) {
            triggersAdapter.submitList(it.toList())
        }
        viewModel.macro.actionsLiveData.observe(viewLifecycleOwner) {
            actionsAdapter.submitList(it.toList())
        }
    }

    override fun onOptionClick(option: Option) {
        // option.onClickSelected(requireActivity(), viewModel.macro)
        // TODO: instead run if it is an action and do nothing if is a trigger
    }

    fun onBackPressed() {
        navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.macro_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_macro -> {
                editMacro()
            }
            R.id.rename_macro -> {
                renameMacro()
            }
            R.id.delete_macro -> {
                deleteMacro()
            }
            R.id.test_actions -> {
                viewModel.macro.runActions(requireActivity())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editMacro() =
        navController.navigate(
            R.id.action_macroDetailsFragment_to_addMacroFragment,
            bundleOf("macro" to viewModel.macro)
        )


    private fun renameMacro() {
        MaterialDialog(requireActivity()).show {
            title(res = R.string.rename_macro_title)
            input(hintRes = R.string.rename_macro_text) { _, text ->
                viewModel.macro.name = text.toString()
                this@MacroDetailsFragment.actionBar.title = viewModel.macro.name
                // TODO: check if name is unique
                viewModel.updateMacro()
            }
            positiveButton(res = R.string.ok)
            negativeButton(res = R.string.cancel)
        }
    }

    private fun deleteMacro() {
        MaterialDialog(requireContext()).show {
            title(res = R.string.delete_macro_title)
            message(res = R.string.delete_macro_text)
            positiveButton(res = R.string.yes) {
                viewModel.macro.deactivate(requireActivity())
                viewModel.deleteMacro()
                viewModel.deletionState.observe(viewLifecycleOwner) {
                    if (it) {
                        navController.navigateUp()
                    }
                }
            }
            negativeButton(res = R.string.no)
        }
    }

    override fun onDestroyView() {
        viewModel.updateMacro()
        // TODO: also when editing trigger receiver should change accordingly etc
        super.onDestroyView()
    }

    companion object {
        const val ARG_MACRO = "macro"
    }
}