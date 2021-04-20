package com.avvlas.coursework2021.domain.model

import android.content.Context
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
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
            trigger.schedule(this, context)
        isActivated = true
    }

    fun deactivate(context: Context) {
        for (trigger in triggers)
            trigger.cancel(this, context)
        isActivated = false
    }

    fun runTest(context: Context) {
        for (action in actions)
            action.execute(context)
    }

    companion object {

    }
}
