package com.avvlas.coursework2021.ui.addmacro.pages

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.options.Option
import com.avvlas.coursework2021.ui.addmacro.AddMacroViewModel
import com.avvlas.coursework2021.ui.addmacro.AddMacroFragment
import com.avvlas.coursework2021.ui.addmacro.pages.listadapters.CategoriesListAdapter
import com.avvlas.coursework2021.ui.addmacro.pages.listadapters.OptionsListAdapter

internal abstract class BaseOptionsFragment<T : Option>(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId), OptionsListAdapter.OnOptionClickListener<T> {

    protected val viewModel: AddMacroViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    protected lateinit var viewPagerFragment: AddMacroFragment
    protected lateinit var adapter: CategoriesListAdapter<T>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerFragment = requireParentFragment() as AddMacroFragment
        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.adapter = CategoriesListAdapter<T>(this).also {
            this.adapter = it
        }
    }
}