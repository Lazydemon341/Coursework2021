package com.avvlas.coursework2021.ui.addmacro.pages.listadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.options.Option

class OptionsListAdapter<T : Option>(
    private val onOptionClickListener: OnOptionClickListener<T>
) : ListAdapter<T, OptionsListAdapter<T>.OptionsViewHolder>(DiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_option, parent, false)
        return OptionsViewHolder(itemView, onOptionClickListener)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnOptionClickListener<T : Option> {
        fun onOptionClick(option: T)
    }

    inner class OptionsViewHolder(
        itemView: View,
        private val onOptionClickListener: OnOptionClickListener<T>
    ) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.option_title_text)

        fun bind(option: T) {
            titleTextView.text = itemView.resources.getString(option.title)
            titleTextView.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    itemView.context,
                    option.icon
                ), null, null, null
            )

            itemView.setOnClickListener {
                onOptionClickListener.onOptionClick(option)
            }
        }
    }

    class DiffCallback<T : Option> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            areItemsTheSame(oldItem, newItem)
    }
}