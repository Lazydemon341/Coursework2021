package com.avvlas.coursework2021.ui.addmacros

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

internal abstract class BasePageFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    protected lateinit var viewPagerFragment: AddMacrosFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerFragment = requireParentFragment() as AddMacrosFragment
    }
}