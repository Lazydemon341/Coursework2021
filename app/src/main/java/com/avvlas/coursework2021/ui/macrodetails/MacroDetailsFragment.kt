package com.avvlas.coursework2021.ui.macrodetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
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
        view.findViewById<ImageView>(R.id.add_trigger_button).setOnClickListener {
            //TODO
        }
        view.findViewById<ImageView>(R.id.add_action_button).setOnClickListener {
            //TODO
        }
    }

    private fun setupRecyclerViews(view: View) {
        view.findViewById<RecyclerView>(R.id.triggers_recycler_view).adapter =
            OptionsListAdapter(this).also {
                this.triggersAdapter = it
            }
        view.findViewById<RecyclerView>(R.id.actions_recycler_view).adapter =
            OptionsListAdapter(this).also {
                this.actionsAdapter = it
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

    override fun onOptionClick(option: Option) =
        option.onClickSelected(requireActivity(), viewModel.macro)

    fun onBackPressed() {
        navController.navigateUp()
    }

    private fun renameMacro() {
        MaterialDialog(requireContext()).show {
            title(text = "Rename macro")
            input(hint = "Enter new name") { _, text ->
                viewModel.macro.name = text.toString()
                this@MacroDetailsFragment.actionBar.title = viewModel.macro.name
                // TODO: check if name is unique
                viewModel.updateMacro()
            }
            positiveButton(text = "OK")
            negativeButton(text = "CANCEL")
        }
    }

    private fun deleteMacro() {
        MaterialDialog(requireContext()).show {
            title(text = "Delete macro")
            message(text = "Are you sure to delete this macro?")
            positiveButton(text = "YES") {
                viewModel.macro.deactivate(requireActivity())
                viewModel.deleteMacro()
                viewModel.deletionState.observe(viewLifecycleOwner) {
                    if (it) {
                        navController.navigateUp()
                    }
                }
            }
            negativeButton(text = "NO")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.macro_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.rename_macro -> {
                renameMacro()
            }
            R.id.delete_macro -> {
                deleteMacro()
            }
            R.id.test_actions -> {
                viewModel.macro.runActions(requireContext())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        internal const val ARG_MACRO = "macro"
    }
}