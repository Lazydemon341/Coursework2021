package com.avvlas.coursework2021.domain.model.options

import android.os.Parcelable
import androidx.annotation.DrawableRes
import java.util.*

abstract class Option(
    @DrawableRes open val icon: Int,
    open val title: String
) : Parcelable
