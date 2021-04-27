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
import com.afollestad.materialdialogs.input.input
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacro.pages.actions.ActionsFragment
import com.avvlas.coursework2021.ui.addmacro.pages.triggers.TriggersFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class AddMacroFragment : Fragment(R.layout.fragment_add_macro) {

    private val viewModel: AddMacroViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Use the ViewModel

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = TITLE
            setDisplayHomeAsUpEnabled(true)
        }
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        initViewPager(view)

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            // TODO: check if valid( at least one trigger and action)
            MaterialDialog(requireContext()).show {
                title(text = "Macro name")
                input(hint = "Enter macro name") { _, text ->
                    viewModel.macro.name = text.toString()
                    // TODO: Check if name if unique
                    viewModel.macro.activate(requireActivity())
                    viewModel.saveMacro()
                    navController.navigateUp()
                }
                positiveButton(text = "OK")
                negativeButton(text = "CANCEL")
            }
        }
    }

    private fun initViewPager(view: View) {
        viewPager = view.findViewById<ViewPager2>(R.id.pager)
        viewPager.adapter = PagerAdapter(
            this@AddMacroFragment,
            TriggersFragment.newInstance(),
            ActionsFragment.newInstance()
        )
        TabLayoutMediator(view.findViewById(R.id.tab_layout), viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> TriggersFragment.TITLE
                1 -> ActionsFragment.TITLE
                else -> TriggersFragment.TITLE
            }
        }.attach()
    }

    fun onBackPressed() {
        requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Quit without saving?")
                .setPositiveButton("yes") { _, _ ->
                    navController.navigateUp()
                }
                .setNegativeButton("no") { _, _ ->
                    // Stay
                }
            builder.show()
        }
    }

    companion object {
        private const val TITLE = "Create macro"
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