package com.avvlas.coursework2021.domain.model

import com.avvlas.coursework2021.domain.model.actions.Action
import com.avvlas.coursework2021.domain.model.triggers.Trigger

// TODO: location, time, battery level, device boot...
// TODO: one to many in db?
data class Macros(
    val triggers: ArrayList<Trigger>,
    val actions: ArrayList<Action>
){
}
