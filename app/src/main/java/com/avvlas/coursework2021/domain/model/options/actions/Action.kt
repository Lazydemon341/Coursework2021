package com.avvlas.coursework2021.domain.model.options.actions

import android.content.Context
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.domain.model.options.Option

abstract class Action(
    @DrawableRes icon: Int,
    title: String
) : Option(icon, title) {

    abstract fun execute(context: Context)
}