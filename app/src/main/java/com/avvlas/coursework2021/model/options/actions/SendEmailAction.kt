package com.avvlas.coursework2021.model.options.actions

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.startActivity
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.parcelize.Parcelize


@Parcelize
class SendEmailAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_screen_rotation_24,
    @StringRes override val title: Int = R.string.send_email_action_title,
    var to: String = "",
    var subject: String = "",
    var text: String = ""
) : Action(icon, title) {

    override suspend fun execute(context: Context) {

        val email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        email.putExtra(Intent.EXTRA_SUBJECT, subject)
        email.putExtra(Intent.EXTRA_TEXT, text)

        email.type = "message/rfc822"

        startActivity(context, Intent.createChooser(email, "Choose an Email client :"), null)
    }

    override fun onClick(activity: Activity, macro: Macro) {
        super.onClick(activity, macro)
    }
}