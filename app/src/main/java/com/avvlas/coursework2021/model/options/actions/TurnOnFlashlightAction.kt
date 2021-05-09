package com.avvlas.coursework2021.model.options.actions

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.triggers.Trigger
import kotlinx.parcelize.Parcelize


@Parcelize
class TurnOnFlashlightAction(
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_flashlight_on_24,
    @StringRes override val title: Int = R.string.turn_on_flashlight_action_title
) : Action(icon, title) {

    override suspend fun execute(context: Context) {
        val camManager =
            context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = camManager.cameraIdList[0] // Usually front camera is at 0 position.

        camManager.setTorchMode(cameraId, true)
    }

    override fun onClickSelected(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(res = title)
            message(res = R.string.delete_action_text)
            positiveButton(res = R.string.yes) {
                macro.removeOption()
            }
            negativeButton(res = R.string.no)
        }
    }
}