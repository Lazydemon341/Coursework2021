package com.avvlas.coursework2021.model.options.actions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.avvlas.coursework2021.App
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
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
                //TODO()
            }
        }

    }

    override fun onClick(activity: Activity, macro: Macro) {
        Permissions.check(
            activity,
            arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS),
            null,
            null,
            object : PermissionHandler() {
                override fun onGranted() {
                    chooseMessageAndDestination(activity, macro)
                }
            }
        )
    }

    private fun chooseMessageAndDestination(activity: Activity, macro: Macro) {
//        val contacts = context.retrieveAllContacts()
//        MaterialDialog(context).show {
//            title(text = "Message and destination")
//            input(hint = "Message text:") { _, text ->
//                messageText = text.toString()
//            }
//            listItemsSingleChoice(
//                items = contacts.map { it.name },
//                initialSelection = 0
//            ) { _, choice, _ ->
//                phoneNumber = contacts[choice].phoneNumber[0]
//                Log.d("myTag", phoneNumber)
//            }
//            positiveButton(text = "OK") {
//                super.onClick(context, macro)
//            }
//            negativeButton(text = "CANCEL")
//        }

//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = ContactsContract.Contacts.CONTENT_TYPE
//        startActivityForResult(activity, intent, PICK_ACCOUNT_REQUEST_CODE, null)

        (activity as AppCompatActivity).supportFragmentManager
            .beginTransaction()
            .add(PickContactFragment.newInstance(), "PickContact")
            .commit()

    }

    class PickContactFragment : Fragment() {

        override fun onAttach(context: Context) {
            super.onAttach(context)

            Log.d(App.TAG, "PickContact onAttach")
            val pickContactLauncher =
                registerForActivityResult(ActivityResultContracts.PickContact()) { result ->
                    val cursor = activity?.contentResolver?.query(result, null, null, null, null)


                    cursor?.close()
                }
            pickContactLauncher.launch(null)
        }

        companion object {
            fun newInstance() = PickContactFragment()
        }
    }


    companion object {
        const val PICK_ACCOUNT_REQUEST_CODE = 1
    }
}