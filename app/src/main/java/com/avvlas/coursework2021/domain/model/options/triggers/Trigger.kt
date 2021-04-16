package com.avvlas.coursework2021.domain.model.options.triggers

import android.content.Context
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.domain.model.Macro
import com.avvlas.coursework2021.domain.model.options.Option
import kotlinx.android.parcel.Parcelize

abstract class Trigger(
    @DrawableRes icon: Int,
    title: String
) : Option(icon, title) {

    abstract fun schedule(macro: Macro, context: Context)

    abstract fun cancel(macro: Macro, context: Context)
}