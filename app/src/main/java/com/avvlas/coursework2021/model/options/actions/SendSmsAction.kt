package com.avvlas.coursework2021.model.options.actions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.PhoneNumberUtils
import android.telephony.SmsManager
import android.text.InputType
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.parcelize.Parcelize


@Parcelize
class SendSmsAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_sms_24,
    @StringRes override val title: Int = R.string.send_sms_action_title,
    var phoneNumber: String = "",
    var messageText: String = ""
) : Action(icon, title) {

    override suspend fun execute(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val smsMgrVar: SmsManager = SmsManager.getDefault()
                smsMgrVar.sendTextMessage(phoneNumber, null, messageText, null, null)
            } catch (ErrVar: Exception) {
                Toast.makeText(
                    context.applicationContext, ErrVar.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
                ErrVar.printStackTrace()
            }
        }

    }

    override fun onClick(context: Context, macro: Macro) {
        Permissions.check(
            context as AppCompatActivity,
            arrayOf(Manifest.permission.SEND_SMS),
            null,
            null,
            object : PermissionHandler() {
                override fun onGranted() {
                    enterPhoneNumber(context, macro)
                }
            }
        )
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
            input(
                prefill = messageText,
                waitForPositiveButton = false,
                allowEmpty = false
            )
            positiveButton(R.string.ok) {
                messageText = it.getInputField().text.toString()
                super.onClick(activity, macro)
            }
            negativeButton(R.string.cancel)
        }
}