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

    abstract fun cancel(appContext: Context, macro: Macro)

    override fun onClick(context: Context, macro: Macro) {
        macro.removeOption()
        macro.addTrigger(this)
    }

    override fun Macro.removeOption() {
        removeTrigger(this@Trigger)
    }
}