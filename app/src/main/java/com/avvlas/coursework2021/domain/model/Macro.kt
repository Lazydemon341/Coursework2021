package com.avvlas.coursework2021.domain.model

import android.content.Context
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avvlas.coursework2021.domain.model.options.actions.Action
import com.avvlas.coursework2021.domain.model.options.triggers.Trigger
import kotlinx.parcelize.Parcelize

// TODO: make a test button which fires all the actions
// TODO: location, time, battery level, device boot...
@Parcelize
data class Macro(
    var name: String = "",
    val triggers: ArrayList<Trigger> = arrayListOf(),
    val actions: ArrayList<Action> = arrayListOf(),
    var activated: Boolean = false
) : Parcelable {

    fun activate(context: Context) {
        for (trigger in triggers)
            trigger.schedule(this, context)
        activated = true
    }

    fun deactivate(context: Context) {
        for (trigger in triggers)
            trigger.cancel(this, context)
        activated = false
    }

    companion object {

    }
}
