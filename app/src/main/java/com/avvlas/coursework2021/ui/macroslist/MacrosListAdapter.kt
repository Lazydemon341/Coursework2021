package com.avvlas.coursework2021.ui.macroslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.Macro

class MacrosListAdapter(
    private val onMacroClickListener: OnMacroClickListener
) : ListAdapter<Macro, MacrosListAdapter.MacrosViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MacrosViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_macro, parent, false)
        return MacrosViewHolder(itemView, onMacroClickListener)
    }

    override fun onBindViewHolder(holder: MacrosViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnMacroClickListener {
        fun onMacroClick(macro: Macro)
    }

    class MacrosViewHolder(
        itemView: View,
        private val onMacroClickListener: OnMacroClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.macro_name_text)
        private val switch = itemView.findViewById<SwitchCompat>(R.id.macro_activation_switch)

        fun bind(macro: Macro) {
            name.text = macro.name
            switch.isChecked = macro.isActivated
            // TODO: deactivate
            itemView.setOnClickListener {
                onMacroClickListener.onMacroClick(macro)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Macro>() {
        override fun areItemsTheSame(oldItem: Macro, newItem: Macro): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Macro, newItem: Macro): Boolean =
            oldItem == newItem
    }
}