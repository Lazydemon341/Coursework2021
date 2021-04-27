package com.avvlas.coursework2021.model.options.actions

import android.app.Activity
import android.content.Context
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.Option

abstract class Action(
    @DrawableRes icon: Int,
    title: String
) : Option(icon, title) {

    abstract suspend fun execute(context: Context)

    open fun onClick(activity: Activity, macro: Macro) {
        macro.actions.add(this)
    }
}