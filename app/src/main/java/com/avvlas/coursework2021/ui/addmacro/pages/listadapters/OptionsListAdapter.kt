package com.avvlas.coursework2021.ui.addmacro.pages.listadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.options.Option
import com.avvlas.coursework2021.domain.model.options.triggers.DayTimeTrigger
import com.avvlas.coursework2021.domain.model.options.triggers.Trigger

class OptionsListAdapter<T : Option> :
    ListAdapter<T, OptionsListAdapter<T>.OptionsViewHolder>(DiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_option, parent, false)
        return OptionsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class OptionsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.option_title_text)

        fun bind(option: T) {
            titleTextView.text = option.title
            titleTextView.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    itemView.context,
                    option.icon
                ), null, null, null
            )

            itemView.setOnClickListener {
                // TODO: move this to fragment
                if (option is Trigger) {
                    when (option) {
                        is DayTimeTrigger -> MaterialDialog(itemView.context).show {
                            dateTimePicker(requireFutureDateTime = true) { _, dateTime ->
                                // TODO: Use dateTime (Calendar)
                            }
                        }
                    }
                }
            }
        }
    }

    class DiffCallback<T : Option> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem::class == newItem::class

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            areItemsTheSame(oldItem, newItem)
    }
}