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

class SelectedOptionsListAdapter<T : Option>(
    private val onOptionClickListener: OnSelectedOptionClickListener<T>
) : ListAdapter<T, SelectedOptionsListAdapter<T>.SelectedOptionsViewHolder>(DiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedOptionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_option, parent, false)
        return SelectedOptionsViewHolder(itemView, onOptionClickListener)
    }

    override fun onBindViewHolder(holder: SelectedOptionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnSelectedOptionClickListener<T : Option> {
        fun onSelectedOptionClick(option: T)
    }

    inner class SelectedOptionsViewHolder(
        itemView: View,
        private val onOptionClickListener: OnSelectedOptionClickListener<T>
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
                onOptionClickListener.onSelectedOptionClick(option)
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