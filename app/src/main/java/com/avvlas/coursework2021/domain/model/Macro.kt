package com.avvlas.coursework2021.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avvlas.coursework2021.domain.model.options.actions.Action
import com.avvlas.coursework2021.domain.model.options.triggers.Trigger

// TODO: location, time, battery level, device boot...
// TODO: one to many in db?
// TODO: how to make lists immutable?
data class Macro(
    var name: String = "",
    val triggers: ArrayList<Trigger> = arrayListOf(),
    val actions: ArrayList<Action> = arrayListOf()
) {
    fun addTrigger(trigger: Trigger) = triggers.add(trigger)
    fun addAction(action: Action) = actions.add(action)
}
