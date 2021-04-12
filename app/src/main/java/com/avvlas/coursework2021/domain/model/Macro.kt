package com.avvlas.coursework2021.domain.model

import com.avvlas.coursework2021.domain.model.options.actions.Action
import com.avvlas.coursework2021.domain.model.options.triggers.Trigger

// TODO: location, time, battery level, device boot...
// TODO: one to many in db?
data class Macro(
    private var _name: String,
    private val triggers: ArrayList<Trigger>,
    private val actions: ArrayList<Action>
) {
    constructor() : this("", arrayListOf<Trigger>(), arrayListOf<Action>())

    fun addTrigger(trigger: Trigger) = triggers.add(trigger)

    fun addAction(action: Action) = actions.add(action)

    var name: String
        get() = _name
        set(value) {
            _name = value
        }

}
