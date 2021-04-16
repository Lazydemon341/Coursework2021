package com.avvlas.coursework2021.domain.model.options.triggers

import android.content.Context
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.domain.model.Macro
import kotlinx.parcelize.Parcelize

@Parcelize
class LocationTrigger(
    @DrawableRes override val icon: Int,
    override val title: String
) : Trigger(icon, title) {

    override fun schedule(macro: Macro, context: Context) {
        TODO("Not yet implemented")
    }

    override fun cancel(macro: Macro, context: Context) {
        TODO("Not yet implemented")
    }
}