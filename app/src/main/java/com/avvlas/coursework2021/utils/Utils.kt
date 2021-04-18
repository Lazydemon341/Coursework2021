package com.avvlas.coursework2021.utils

import android.content.res.Resources
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.avvlas.coursework2021.domain.model.Macro
import kotlin.math.roundToInt


object Utils {
    fun dpToPx(resources: Resources, dp: Int) =
        (dp * resources.displayMetrics.density).roundToInt()

    val FragmentManager.currentNavigationFragment: Fragment?
        get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()

    val Macro.Companion.CREATOR: Parcelable.Creator<Macro>
        get() = ParcelableCreators.getMacroCreator()
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