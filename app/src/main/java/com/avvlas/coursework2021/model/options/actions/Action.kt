package com.avvlas.coursework2021.model.options.actions

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.Option

abstract class Action(
    @DrawableRes icon: Int,
    @StringRes title: Int
) : Option(icon, title) {

    abstract suspend fun execute(context: Context)

    override fun onClick(context: Context, macro: Macro) {
        macro.removeOption()
        macro.addAction(this)
    }

    override fun Macro.removeOption() =
        removeAction(this@Action)

}