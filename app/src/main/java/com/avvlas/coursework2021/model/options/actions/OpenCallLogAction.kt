package com.avvlas.coursework2021.model.options.actions

import android.content.Context
import android.content.Intent
import android.provider.CallLog
import androidx.annotation.DrawableRes
import com.avvlas.coursework2021.R
import kotlinx.parcelize.Parcelize


@Parcelize
class OpenCallLogAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_screen_rotation_24,
    override val title: String = "Open Call Log",
) : Action(icon, title) {

    override suspend fun execute(context: Context) {
        val intentCallLog = Intent()
        intentCallLog.action = Intent.ACTION_VIEW
        intentCallLog.type = CallLog.Calls.CONTENT_TYPE
        context.startActivity(intentCallLog)
    }
}