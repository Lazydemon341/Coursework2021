package com.avvlas.coursework2021.model.options.actions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.utils.Utils.showInputDialog
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.parcelize.Parcelize


@Parcelize
class SendSmsAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_screen_rotation_24,
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
                Toast.makeText(
                    context.applicationContext, "Message Sent",
                    Toast.LENGTH_LONG
                ).show()
            } catch (ErrVar: Exception) {
                Toast.makeText(
                    context.applicationContext, ErrVar.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
                ErrVar.printStackTrace()
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //TODO: require permissions?
            }
        }

    }

    override fun onClick(context: Context, macro: Macro) {
        Permissions.check(
            context as AppCompatActivity,
            arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS),
            null,
            null,
            object : PermissionHandler() {
                override fun onGranted() {
//                    requireDestination(activity, macro)
                    enterPhoneNumber(context, macro)
                }
            }
        )
    }

    private fun enterPhoneNumber(activity: Activity, macro: Macro) =
        activity.showInputDialog(
            // TODO: check number format
            // TODO: add button to pick from contacts
            title = "Enter phone number",
            hint = "Phone number:",
            onInput = { _, text ->
                phoneNumber = text.toString()
            },
            onPositiveButtonClick = {
                enterMessageText(activity, macro)
            })


    private fun enterMessageText(activity: Activity, macro: Macro) =
        activity.showInputDialog(
            title = "Enter message text",
            hint = "Message text:",
            onInput = { _, text ->
                phoneNumber = text.toString()
            },
            onPositiveButtonClick = {
                super.onClick(activity, macro)
            }
        )


//    private fun requireDestination(activity: Activity, macro: Macro) {
//        (activity as AppCompatActivity).supportFragmentManager
//            .beginTransaction()
//            .add(PickContactFragment(this, macro), "PickContact")
//            .commit()
//
//    }

//    private fun requireMessageText(activity: Activity, macro: Macro) {
//        MaterialDialog(activity).show {
//            title(text = "Message text")
//            input { _, text ->
//                messageText = text.toString()
//            }
//            positiveButton(text = "OK") {
//                super.onClick(activity, macro)
//            }
//            negativeButton(text = "CANCEL")
//        }
//    }

//    class PickContactFragment(private val sendSmsAction: SendSmsAction, private val macro: Macro) :
//        Fragment() {
//
//        override fun onAttach(context: Context) {
//            super.onAttach(context)
//
//            Log.d(App.TAG, "PickContact onAttach")
//
//            registerForActivityResult(ActivityResultContracts.PickContact()) { result ->
//                requireActivity().contentResolver.query(result, null, null, null, null)?.use {
//                    if (it.moveToFirst()) {
//                        do {
//                            val name =
//                                it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
//                                    ?: ""
//                            val hasPhoneNumber =
//                                it.getString(it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
//                                    .toInt()
//                            Log.d(App.TAG, "$name, $hasPhoneNumber")
////                            sendSmsAction.phoneNumber =
////                                ContactsContract.Contacts.retrievePhoneNumber(contactId)
//
//                        } while (it.moveToNext())
//                    }
//                }
//
//                sendSmsAction.requireMessageText(requireActivity(), macro)
//            }
//                .launch(null)
//        }
//    }
}