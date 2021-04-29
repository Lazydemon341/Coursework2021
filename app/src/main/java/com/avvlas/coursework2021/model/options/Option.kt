package com.avvlas.coursework2021.model.options

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

abstract class Option(
    @Transient @DrawableRes open val icon: Int = 0,
    @Transient @StringRes open val title: Int = 0
) : Parcelable
