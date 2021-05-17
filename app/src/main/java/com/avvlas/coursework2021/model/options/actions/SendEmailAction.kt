package com.avvlas.coursework2021.model.options.actions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.InputType
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.startActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlinx.parcelize.Parcelize


@Parcelize
class SendEmailAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_email_24,
    @StringRes override val title: Int = R.string.send_email_action_title,
    var to: String = "",
    var subject: String = "",
    var text: String = ""
) : Action(icon, title) {

    override suspend fun execute(context: Context) {

        val email = Intent(Intent.ACTION_SENDTO)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        email.putExtra(Intent.EXTRA_SUBJECT, subject)
        email.putExtra(Intent.EXTRA_TEXT, text)
        email.data = Uri.parse("mailto:")
        email.type = "message/rfc822"

        startActivity(context, email, null)
    }

    override fun onClick(context: Context, macro: Macro) {
        enterDestination(context, macro)
    }

    private fun enterDestination(context: Context, macro: Macro) =
        MaterialDialog(context).show {
            title(res = R.string.enter_email_address)
            input(
                prefill = to,
                waitForPositiveButton = false,
                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                allowEmpty = false
            ) { dialog, text ->
                dialog.setActionButtonEnabled(
                    WhichButton.POSITIVE,
                    android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
                )
            }
            positiveButton(R.string.ok) {
                to = it.getInputField().text.toString()
                enterSubject(context, macro)
            }
            negativeButton(R.string.cancel)
        }


    private fun enterSubject(context: Context, macro: Macro) =
        MaterialDialog(context).show {
            title(res = R.string.enter_mail_subject)
            input(
                prefill = subject,
            )
            positiveButton(R.string.ok) {
                subject = it.getInputField().text.toString()
                enterText(context, macro)
            }
            negativeButton(R.string.cancel)
        }

    private fun enterText(context: Context, macro: Macro) =
        MaterialDialog(context).show {
            title(res = R.string.enter_mail_text)
            input(
                prefill = text,
            )
            positiveButton(R.string.ok) {
                text = it.getInputField().text.toString()
                super.onClick(context, macro)
            }
            negativeButton(R.string.cancel)
        }
}