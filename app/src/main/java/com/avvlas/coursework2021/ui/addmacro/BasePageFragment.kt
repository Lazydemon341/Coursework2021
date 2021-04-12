package com.avvlas.coursework2021.ui.addmacro

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels

internal abstract class BasePageFragment(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    protected val viewModel: AddMacroViewModel by activityViewModels()
    protected lateinit var viewPagerFragment: AddMacrosFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerFragment = requireParentFragment() as AddMacrosFragment
    }
}