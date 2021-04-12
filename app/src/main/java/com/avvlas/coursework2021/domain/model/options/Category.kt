package com.avvlas.coursework2021.domain.model.options

import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.domain.model.options.Option

data class Category(
    @DrawableRes val icon: Int,
    val title: String,
    val items: List<Option>
)
