package com.avvlas.coursework2021.model

import android.app.Activity
import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avvlas.coursework2021.model.options.actions.Action
import com.avvlas.coursework2021.model.options.triggers.Trigger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

// TODO: location, time, battery level, device boot, settings change...
@Parcelize
data class Macro(
    var id: Long = 0,
    var name: String = "",
    val triggers: ArrayList<Trigger> = arrayListOf(),
    val actions: ArrayList<Action> = arrayListOf(),
    var isActivated: Boolean = false
) : Parcelable {

    @IgnoredOnParcel
    private val _triggersLiveData = MutableLiveData(triggers)

    @IgnoredOnParcel
    val triggersLiveData: LiveData<ArrayList<Trigger>> = _triggersLiveData

    fun addTrigger(trigger: Trigger) {
        _triggersLiveData.value?.add(trigger)
        _triggersLiveData.value = _triggersLiveData.value
    }

    fun removeTrigger(trigger: Trigger) {
        _triggersLiveData.value?.remove(trigger)
        _triggersLiveData.value = _triggersLiveData.value
    }

    @IgnoredOnParcel
    private val _actionsLiveData = MutableLiveData(actions)

    @IgnoredOnParcel
    val actionsLiveData: LiveData<ArrayList<Action>> = _actionsLiveData

    fun addAction(action: Action) {
        _actionsLiveData.value?.add(action)
        _actionsLiveData.value = _actionsLiveData.value
    }

    fun removeAction(action: Action) {
        _actionsLiveData.value?.remove(action)
        _actionsLiveData.value = _actionsLiveData.value
    }

    fun activate(activity: Activity) {
        for (trigger in triggers)
            trigger.schedule(activity.applicationContext, this)
        isActivated = true
    }

    fun deactivate(activity: Activity) {
        for (trigger in triggers)
            trigger.cancel(activity.applicationContext, this)
        isActivated = false
    }

    fun runActions(context: Context) =
        CoroutineScope(SupervisorJob() + Dispatchers.Default).launch {
            for (action in actions) {
                action.execute(context)
            }
        }

    companion object {

    }
}
