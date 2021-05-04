package com.avvlas.coursework2021.ui.macrodetails

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

class OptionsListAdapter(
    private val onOptionClickListener: OnOptionClickListener
) : ListAdapter<Option, OptionsListAdapter.OptionsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_option, parent, false)
        return OptionsViewHolder(itemView, onOptionClickListener)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnOptionClickListener {
        fun onOptionClick(option: Option)
    }

    inner class OptionsViewHolder(
        itemView: View,
        private val onOptionClickListener: OnOptionClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.option_title_text)

        fun bind(option: Option) {
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

    class DiffCallback : DiffUtil.ItemCallback<Option>() {
        override fun areItemsTheSame(oldItem: Option, newItem: Option): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Option, newItem: Option): Boolean =
            areItemsTheSame(oldItem, newItem)
    }
}