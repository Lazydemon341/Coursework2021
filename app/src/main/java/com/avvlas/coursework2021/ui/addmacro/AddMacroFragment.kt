package com.avvlas.coursework2021.ui.addmacro

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacro.pages.actions.ActionsFragment
import com.avvlas.coursework2021.ui.addmacro.pages.triggers.TriggersFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMacroFragment : Fragment(R.layout.fragment_add_macro) {

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
    }

    private fun initViewPager(view: View) {
        viewPager = view.findViewById<ViewPager2>(R.id.pager)
        viewPager.adapter = PagerAdapter(this@AddMacroFragment)
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
        @JvmStatic
        fun newInstance() = AddMacroFragment()

        private val tabsTitles = arrayOf("Triggers", "Actions")

        private const val TITLE = "Create macro"
    }
}

class PagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TriggersFragment.newInstance()
            1 -> ActionsFragment.newInstance()
            else -> TriggersFragment.newInstance()
        }
    }

    companion object {
        private const val ITEM_COUNT = 2
    }
}