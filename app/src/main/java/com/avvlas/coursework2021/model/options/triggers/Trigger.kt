package com.avvlas.coursework2021.model.options.triggers

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.Option

abstract class Trigger(
    @DrawableRes icon: Int,
    @StringRes title: Int
) : Option(icon, title) {

    abstract fun schedule(appContext: Context, macro: Macro)

    abstract fun cancel(context: Context, macro: Macro)

    open fun onClick(context: Context, macro: Macro){
        macro.triggers.add(this)
    }
}