package com.avvlas.coursework2021.domain.model

import android.content.Context
import android.os.Parcelable
import com.avvlas.coursework2021.domain.model.options.actions.Action
import com.avvlas.coursework2021.domain.model.options.triggers.Trigger
import kotlinx.parcelize.Parcelize

// TODO: location, time, battery level, device boot, settings change...
@Parcelize
data class Macro(
    var name: String = "",
    val triggers: ArrayList<Trigger> = arrayListOf(),
    val actions: ArrayList<Action> = arrayListOf(),
    var isActivated: Boolean = false
) : Parcelable {

    fun activate(context: Context) {
        for (trigger in triggers)
            trigger.schedule(context, this)
        isActivated = true
    }

    fun deactivate(context: Context) {
        for (trigger in triggers)
            trigger.cancel(context, this)
        isActivated = false
    }

    fun runTest(context: Context) {
        for (action in actions)
            action.execute(context)
    }

    companion object {

    }
}
