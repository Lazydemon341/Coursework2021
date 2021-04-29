package com.avvlas.coursework2021.model.options.actions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import java.net.URLEncoder


@Parcelize
class SendWhatsappMessageAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_screen_rotation_24,
    @StringRes override val title: Int = R.string.send_whatsapp_message_action_title,
    var phoneNumber: String = "",
    var messageText: String = ""
) : Action(icon, title) {

    override suspend fun execute(context: Context) = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager
        val i = Intent(Intent.ACTION_VIEW)

        try {
            val url =
                "https://api.whatsapp.com/send?phone=$phoneNumber&text=" + URLEncoder.encode(
                    messageText, "UTF-8"
                )
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                context.startActivity(i)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(activity: Activity, macro: Macro) {
        super.onClick(activity, macro)
    }
}