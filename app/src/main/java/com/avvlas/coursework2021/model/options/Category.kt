package com.avvlas.coursework2021.model.options

import androidx.annotation.DrawableRes

data class Category<out T : Option>(
    @DrawableRes val icon: Int,
    val title: String,
    val items: List<T>
)
