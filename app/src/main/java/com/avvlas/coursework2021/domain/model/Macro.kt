package com.avvlas.coursework2021.domain.model

import com.avvlas.coursework2021.domain.model.actions.Action
import com.avvlas.coursework2021.domain.model.triggers.Trigger

// TODO: location, time, battery level, device boot...
// TODO: one to many in db?
data class Macro(
    private val triggers: ArrayList<Trigger>,
    private val actions: ArrayList<Action>
) {
    constructor() : this(arrayListOf<Trigger>(), arrayListOf<Action>())

    fun addTrigger(trigger: Trigger) = triggers.add(trigger)

    fun addAction(action: Action) = actions.add(action)
}
