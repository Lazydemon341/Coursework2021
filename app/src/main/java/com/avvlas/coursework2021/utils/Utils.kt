package com.avvlas.coursework2021.utils

import android.content.Context
import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import kotlin.math.roundToInt


object Utils {
    fun dpToPx(resources: Resources, dp: Int) =
        (dp * resources.displayMetrics.density).roundToInt()

    val FragmentManager.currentNavigationFragment: Fragment?
        get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()

    val Macro.Companion.CREATOR: Parcelable.Creator<Macro>
        get() = ParcelableCreators.getMacroCreator()

    fun Context.showInputDialog(
        title: String,
        hint: String,
        onInput: (dialog: MaterialDialog, text: CharSequence) -> Unit,
        onPositiveButtonClick: (dialog: MaterialDialog) -> Unit
    ) {
        MaterialDialog(this).show {
            title(text = title)
            input(hint = hint, callback = onInput)
            positiveButton(R.string.ok, click = onPositiveButtonClick)
            negativeButton(R.string.cancel)
        }
    }
}

object Parcelables {

    fun Parcelable.toByteArray(): ByteArray {
        val parcel = Parcel.obtain()
        this.writeToParcel(parcel, 0)
        val result = parcel.marshall()
        parcel.recycle()
        return result
    }

    fun <T : Parcelable> ByteArray.toParcelable(
        creator: Parcelable.Creator<T>
    ): T {
        val parcel = Parcel.obtain()
        parcel.unmarshall(this, 0, this.size)
        parcel.setDataPosition(0)
        val result = creator.createFromParcel(parcel)
        parcel.recycle()
        return result
    }
}