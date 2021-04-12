package com.avvlas.coursework2021.ui.addmacro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacro.pages.actions.ActionsFragment
import com.avvlas.coursework2021.ui.addmacro.pages.triggers.TriggersFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMacrosFragment : Fragment(R.layout.fragment_add_macro) {

    private val viewModel: AddMacroViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Use the ViewModel

        (requireActivity() as AppCompatActivity).supportActionBar?.title = TITLE
        initViewPager(view)
    }

    private fun initViewPager(view: View) {
        viewPager = view.findViewById<ViewPager2>(R.id.pager)
        viewPager.adapter = PagerAdapter(this@AddMacrosFragment)
        TabLayoutMediator(view.findViewById(R.id.tab_layout), viewPager) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddMacrosFragment()

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