package com.avvlas.coursework2021.ui.addmacros

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.ui.addmacros.actions.ActionsFragment
import com.avvlas.coursework2021.ui.addmacros.triggers.TriggersFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMacrosFragment : Fragment(R.layout.add_macros_fragment) {

    private val viewModel: AddMacrosViewModel by viewModels()
    private lateinit var textView: TextView
    private lateinit var viewPager: ViewPager2

    private var x: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.textView)
        // TODO: Use the ViewModel

        initViewPager(view)
    }

    private fun initViewPager(view: View) {
        viewPager = view.findViewById<ViewPager2>(R.id.pager)
        viewPager.adapter = PagerAdapter(this@AddMacrosFragment)
        TabLayoutMediator(view.findViewById(R.id.tab_layout), viewPager) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()
    }

    internal fun increment() {
        x += 1
        textView.text = x.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddMacrosFragment()

        private val tabsTitles: List<String> = listOf("Triggers", "Actions")
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