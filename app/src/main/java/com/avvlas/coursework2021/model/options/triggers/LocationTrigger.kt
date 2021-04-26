package com.avvlas.coursework2021.model.options.triggers

import android.content.Context
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.model.Macro
import kotlinx.parcelize.Parcelize

@Parcelize
class LocationTrigger(
    @DrawableRes override val icon: Int,
    override val title: String
) : Trigger(icon, title) {

    override fun schedule(appContext: Context, macro: Macro) {
        TODO("Not yet implemented")
    }

    override fun cancel(context: Context, macro: Macro) {
        TODO("Not yet implemented")
    }

    override fun onClick(context: Context, macro: Macro) {
        TODO("Not yet implemented")
    }
}