package com.avvlas.coursework2021.ui.addmacro.pages

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.avvlas.coursework2021.ui.addmacro.AddMacroViewModel
import com.avvlas.coursework2021.ui.addmacro.AddMacrosFragment

internal abstract class BasePageFragment(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    protected val viewModel: AddMacroViewModel by activityViewModels()
    protected lateinit var viewPagerFragment: AddMacrosFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerFragment = requireParentFragment() as AddMacrosFragment
    }
}