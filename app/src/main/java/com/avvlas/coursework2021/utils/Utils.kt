package com.avvlas.coursework2021.utils

import android.content.res.Resources
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import kotlin.math.roundToInt


object Utils {
    fun dpToPx(resources: Resources, dp: Int) =
        (dp * resources.displayMetrics.density).roundToInt()
}