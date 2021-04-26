package com.avvlas.coursework2021.model.options.actions

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.utils.ContactUtils.retrieveAllContacts
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.parcelize.Parcelize


@Parcelize
class SendSmsAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_screen_rotation_24,
    override val title: String = "Send SMS",
    var phoneNumber: String = "",
    var messageText: String = ""
) : Action(icon, title) {

    override suspend fun execute(context: Context) {
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.SEND_SMS
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            try {
//                val smsMgrVar: SmsManager = SmsManager.getDefault()
//                smsMgrVar.sendTextMessage(phoneNumber, null, messageText, null, null)
//                Toast.makeText(
//                    context.applicationContext, "Message Sent",
//                    Toast.LENGTH_LONG
//                ).show()
//            } catch (ErrVar: Exception) {
//                Toast.makeText(
//                    context.applicationContext, ErrVar.message.toString(),
//                    Toast.LENGTH_LONG
//                ).show()
//                ErrVar.printStackTrace()
//            }
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(context, arrayOf<String>(Manifest.permission.SEND_SMS), 10)
//            }
//        }

    }

    override fun onClick(context: Context, macro: Macro) {
        Permissions.check(
            context,
            arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS),
            null,
            null,
            object : PermissionHandler() {
                override fun onGranted() {
                    chooseMessageAndDestination(context, macro)
                }
            }
        )
    }

    private fun chooseMessageAndDestination(context: Context, macro: Macro) {
        val contacts = context.retrieveAllContacts()
        MaterialDialog(context).show {
            title(text = "Message and destination")
            input(hint = "Message text:") { _, text ->
                messageText = text.toString()
            }
            listItemsSingleChoice(
                items = contacts.map { it.name },
                initialSelection = 0
            ) { _, choice, _ ->
                phoneNumber = contacts[choice].phoneNumber[0]
                Log.d("myTag", phoneNumber)
            }
            positiveButton(text = "OK") {
                super.onClick(context, macro)
            }
            negativeButton(text = "CANCEL")
        }
    }

//    private fun getContactsList(context: Context): ArrayList<String> {
//        val nameList = arrayListOf<String>();
//        val cr = context.contentResolver;
//        val cur = cr.query(
//            ContactsContract.Contacts.CONTENT_URI,
//            null, null, null, null
//        )
//        if (cur?.count ?: 0 > 0) {
//            while (cur != null && cur.moveToNext()) {
//                val id = cur.getString(
//                    cur.getColumnIndex(ContactsContract.Contacts._ID)
//                );
//                val name = cur.getString(
//                    cur.getColumnIndex(
//                        ContactsContract.Contacts.DISPLAY_NAME
//                    )
//                )
//                if (name != null)
//                    nameList.add(name)
//
//                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
//                    val pCur = cr.query(
//                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                        null,
//                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                        arrayOf(id), null
//                    )
//                    while (pCur?.moveToNext() == true) {
//                        val phoneNo = pCur.getString(
//                            pCur.getColumnIndex(
//                                ContactsContract.CommonDataKinds.Phone.NUMBER
//                            )
//                        )
//                    }
//                    pCur?.close();
//                }
//            }
//        }
//        cur?.close()
//        Log.d("myTag", nameList.toString())
//        return nameList.apply { sort() };
//    }
}