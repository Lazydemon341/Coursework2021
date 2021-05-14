package com.avvlas.coursework2021.ui.addmacro

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.triggers.Trigger
import com.avvlas.coursework2021.ui.addmacro.pages.actions.ActionsFragment
import com.avvlas.coursework2021.ui.addmacro.pages.triggers.TriggersFragment
import com.avvlas.coursework2021.ui.macrodetails.MacroDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
internal class AddMacroFragment : Fragment(R.layout.fragment_add_macro) {

    val viewModel: AddMacroViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title =
                if (viewModel.isNewMacro)
                    getString(R.string.add_macro_fragment_title)
                else
                    getString(R.string.edit_macro_fragment_title)

            setDisplayHomeAsUpEnabled(true)
        }

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        initViewPager(view)

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            onFabClick()
        }
    }

    private fun getArgs() =
        arguments?.getParcelable<Macro>(MacroDetailsFragment.ARG_MACRO)?.let {
            viewModel.macro = it
            viewModel.isNewMacro = false
        }


    private fun onFabClick() {
        if (viewModel.macro.actions.isNotEmpty() && viewModel.macro.triggers.isNotEmpty()) {
            if (viewModel.isNewMacro) {
                enterNameAndSaveMacro()
            } else {
                viewModel.saveMacro()
                navController.navigateUp()
            }
        } else {
            showInvalidMacroMessage()
        }
    }

    private fun showInvalidMacroMessage() =
        MaterialDialog(requireContext()).show {
            title(res = R.string.invalid_macro_title)
            message(res = R.string.invalid_macro_message)
            positiveButton(text = "OK")
        }


    private fun enterNameAndSaveMacro() {
        val namesDeferred = viewModel.getMacrosNames()

        MaterialDialog(requireContext()).show {
            title(R.string.enter_macro_name)
            input(
                hintRes = R.string.macro_name_hint,
                waitForPositiveButton = false
            ) { dialog, text ->

                runBlocking {
                    val names = namesDeferred.await()
                    val isValid = !names.contains(text.toString())
                    // TODO: change to string res for localisation
                    dialog.getInputField().error =
                        if (isValid) null else "Macro name must be unique!"
                    dialog.setActionButtonEnabled(WhichButton.POSITIVE, isValid)

                    if (isValid)
                        viewModel.macro.name = text.toString()
                }
            }
            positiveButton(R.string.ok) {
                viewModel.saveMacro()
                viewModel.macro.isActivated = true
                viewModel.macro.activate(requireActivity())
                navController.navigateUp()
            }
            negativeButton(R.string.cancel)
        }
    }


    private fun initViewPager(view: View) {
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = PagerAdapter(
            this@AddMacroFragment,
            TriggersFragment.newInstance(),
            ActionsFragment.newInstance()
        )
        TabLayoutMediator(view.findViewById(R.id.tab_layout), viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.triggers_title)
                1 -> getString(R.string.actions_title)
                else -> getString(R.string.triggers_title)
            }
        }.attach()
    }

    fun onBackPressed() {
        requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.quit_without_saving))
                .setPositiveButton(R.string.yes) { _, _ ->
                    navController.navigateUp()
                }
                .setNegativeButton(R.string.no) { _, _ -> // Stay
                }
            builder.show()
        }
    }

    fun addTriggerToMacro(trigger: Trigger) {
        viewModel.macro.addTrigger(trigger)
    }
}

internal class PagerAdapter(
    fragment: Fragment,
    private val triggersFragment: TriggersFragment,
    private val actionsFragment: ActionsFragment
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> triggersFragment
            1 -> actionsFragment
            else -> triggersFragment
        }
    }

    companion object {
        private const val ITEM_COUNT = 2
    }
}