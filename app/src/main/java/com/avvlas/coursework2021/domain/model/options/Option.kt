package com.avvlas.coursework2021.domain.model.options

import androidx.annotation.DrawableRes
import java.util.*

abstract class Option(
    @DrawableRes val icon: Int,
    val title: String
) {
    override fun equals(other: Any?): Boolean =
        (other is Option)
                && icon == other.icon
                && title == other.title

    override fun hashCode(): Int =
        Objects.hash(icon, title)
}
