package com.avvlas.coursework2021.model.options

import android.content.Context
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro

abstract class Option(
    @Transient @DrawableRes open val icon: Int = 0,
    @Transient @StringRes open val title: Int = 0
) : Parcelable {

    abstract fun onClick(context: Context, macro: Macro)

    abstract fun removeFromMacro(macro: Macro)

    fun onClickSelected(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(res = title)
            listItems(R.array.selected_option_options) { _, index, _ ->
                when (index) {
                    0 -> onClick(context, macro)
                    1 -> removeFromMacro(macro)
                }
            }
        }
    }
}