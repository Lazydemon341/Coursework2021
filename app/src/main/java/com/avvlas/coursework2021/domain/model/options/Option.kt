package com.avvlas.coursework2021.domain.model.options

import android.os.Parcelable
import androidx.annotation.DrawableRes

abstract class Option(
    @Transient @DrawableRes open val icon: Int = 0,
    @Transient open val title: String = ""
) : Parcelable
