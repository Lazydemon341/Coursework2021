package com.avvlas.coursework2021.domain.model.options.triggers

import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.domain.model.options.Option

abstract class Trigger(
    @DrawableRes icon: Int,
    title : String
) : Option(icon, title){
}