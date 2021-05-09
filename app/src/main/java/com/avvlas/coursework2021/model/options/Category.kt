package com.avvlas.coursework2021.model.options

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category<out T : Option>(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val items: List<T>
)
