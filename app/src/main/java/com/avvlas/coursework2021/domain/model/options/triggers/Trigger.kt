package com.avvlas.coursework2021.domain.model.options.triggers

import android.content.Context
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.domain.model.Macro
import com.avvlas.coursework2021.domain.model.options.Option

abstract class Trigger(
    @DrawableRes icon: Int,
    title: String
) : Option(icon, title) {

    abstract fun schedule(context: Context, macro: Macro)

    abstract fun cancel(context: Context, macro: Macro)

    open fun onClick(context: Context, macro: Macro){
        macro.triggers.add(this)
    }
}