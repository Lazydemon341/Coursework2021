package com.avvlas.coursework2021.model.options.actions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.PhoneNumberUtils
import android.text.InputType
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import java.net.URLEncoder


@Parcelize
class SendWhatsappMessageAction(
    @DrawableRes override val icon: Int = R.drawable.ic_whatsapp,
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

    override fun onClick(context: Context, macro: Macro) {
        enterPhoneNumber(context as AppCompatActivity, macro)
    }

    private fun enterPhoneNumber(activity: Activity, macro: Macro) =
        MaterialDialog(activity).show {
            title(res = R.string.enter_phone_number)
            input(
                prefill = phoneNumber,
                waitForPositiveButton = false,
                inputType = InputType.TYPE_CLASS_PHONE,
                allowEmpty = false
            ) { dialog, text ->
                dialog.setActionButtonEnabled(
                    WhichButton.POSITIVE,
                    PhoneNumberUtils.isGlobalPhoneNumber(text.toString())
                )
            }
            positiveButton(R.string.ok) {
                phoneNumber = it.getInputField().text.toString()
                enterMessageText(activity, macro)
            }
            negativeButton(R.string.cancel)
        }


    private fun enterMessageText(activity: Activity, macro: Macro) =
        MaterialDialog(activity).show {
            title(res = R.string.enter_message_text)
            input(prefill = messageText, waitForPositiveButton = false, allowEmpty = false)
            positiveButton(R.string.ok) {
                messageText = it.getInputField().text.toString()
                super.onClick(activity, macro)
            }
            negativeButton(R.string.cancel)
        }
}