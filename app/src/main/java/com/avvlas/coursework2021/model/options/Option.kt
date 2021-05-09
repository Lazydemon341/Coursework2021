package com.avvlas.coursework2021.model.options

import android.content.Context
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.Macro
import com.avvlas.coursework2021.model.options.triggers.Trigger

abstract class Option(
    @Transient @DrawableRes open val icon: Int = 0,
    @Transient @StringRes open val title: Int = 0
) : Parcelable {

    abstract fun onClick(context: Context, macro: Macro)

    abstract fun Macro.removeOption()

    open fun onClickSelected(context: Context, macro: Macro) {
        MaterialDialog(context).show {
            title(res = title)
            listItems(R.array.selected_option_options) { _, index, _ ->
                when (index) {
                    0 -> {
                        if (this@Option is Trigger)
                            this@Option.cancel(context.applicationContext, macro)
                        onClick(context, macro)
                        if (this@Option is Trigger)
                            this@Option.schedule(context.applicationContext, macro)
                    }
                    1 -> macro.removeOption()
                }
            }
        }
    }
}