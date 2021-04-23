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
    private val onMacroClickListener: OnMacroClickListener,
    private val onMacroSwitchListener: OnMacroSwitchListener
) : ListAdapter<Macro, MacrosListAdapter.MacrosViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MacrosViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_macro, parent, false)
        return MacrosViewHolder(itemView, onMacroClickListener, onMacroSwitchListener)
    }

    override fun onBindViewHolder(holder: MacrosViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnMacroClickListener {
        fun onMacroClick(macro: Macro)
    }

    interface OnMacroSwitchListener {
        fun onMacroSwitch(macro: Macro)
    }

    override fun onViewRecycled(holder: MacrosViewHolder) {
        super.onViewRecycled(holder)
        holder.switch.setOnCheckedChangeListener(null)
    }

    class MacrosViewHolder(
        itemView: View,
        private val onMacroClickListener: OnMacroClickListener,
        private val onMacroSwitchListener: OnMacroSwitchListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.macro_name_text)
        internal val switch = itemView.findViewById<SwitchCompat>(R.id.macro_activation_switch)

        fun bind(macro: Macro) {
            switch.isClickable = false
            name.text = macro.name
            switch.isChecked = macro.isActivated
            itemView.setOnClickListener {
                onMacroClickListener.onMacroClick(macro)
            }
            switch.setOnCheckedChangeListener { buttonView, isChecked ->
                // TODO: change activation state
                onMacroSwitchListener.onMacroSwitch(macro)
            }
            switch.isClickable = true
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Macro>() {
        override fun areItemsTheSame(oldItem: Macro, newItem: Macro): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Macro, newItem: Macro): Boolean =
            oldItem == newItem
    }
}