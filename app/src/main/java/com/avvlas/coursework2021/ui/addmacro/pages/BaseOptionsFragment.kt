package com.avvlas.coursework2021.ui.addmacro.pages

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.options.Option
import com.avvlas.coursework2021.ui.addmacro.AddMacroFragment
import com.avvlas.coursework2021.ui.addmacro.AddMacroViewModel
import com.avvlas.coursework2021.ui.addmacro.pages.listadapters.CategoriesListAdapter
import com.avvlas.coursework2021.ui.addmacro.pages.listadapters.OptionsListAdapter
import com.avvlas.coursework2021.ui.addmacro.pages.listadapters.SelectedOptionsListAdapter
import com.avvlas.coursework2021.utils.GridSpacingItemDecoration
import com.avvlas.coursework2021.utils.Utils

internal abstract class BaseOptionsFragment<T : Option>(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId),
    OptionsListAdapter.OnOptionClickListener<T>,
    SelectedOptionsListAdapter.OnSelectedOptionClickListener<T> {

    protected val viewModel: AddMacroViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    protected lateinit var viewPagerFragment: AddMacroFragment
    protected lateinit var categoriesAdapter: CategoriesListAdapter<T>
    protected lateinit var selectedOptionsAdapter: SelectedOptionsListAdapter<T>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerFragment = requireParentFragment() as AddMacroFragment
        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.categories_recycler_view).adapter =
            CategoriesListAdapter<T>(this).also {
                categoriesAdapter = it
            }

        view.findViewById<RecyclerView>(R.id.selected_options_recycler_view).apply {
            adapter =
                SelectedOptionsListAdapter<T>(this@BaseOptionsFragment).also {
                    selectedOptionsAdapter = it
                }
            addItemDecoration(
                GridSpacingItemDecoration(
                    1,
                    Utils.dpToPx(resources, 8),
                    false
                )
            )
        }
    }

    override fun onSelectedOptionClick(option: T) {

    }
}