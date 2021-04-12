package com.avvlas.coursework2021.ui.addmacro.pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.options.Category
import com.avvlas.coursework2021.domain.model.options.Option
import kotlin.math.roundToInt

class CategoriesListAdapter<T : Option> :
    ListAdapter<Category<T>, CategoriesListAdapter<T>.CategoriesViewHolder>(DiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_options_category, parent, false)
        return CategoriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class CategoriesViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.category_title_text)
        private val optionsRecyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view)
        private lateinit var adapter: OptionsListAdapter<T>

        fun bind(category: Category<T>) {
            setTitleAndIcon(category)

            optionsRecyclerView.apply {
                optionsRecyclerView.adapter = OptionsListAdapter<T>().also {
                    this@CategoriesViewHolder.adapter = it
                }
                addItemDecoration(
                    GridSpacingItemDecoration(
                        2,
                        (8 * resources.displayMetrics.density).roundToInt(),
                        false
                    )
                )
            }
            adapter.submitList(category.items)
        }

        private fun setTitleAndIcon(category: Category<T>) {
            titleTextView.text = category.title
            titleTextView.setCompoundDrawables(
                ContextCompat.getDrawable(
                    itemView.context,
                    category.icon
                ), null, null, null
            )

        }
    }

    class DiffCallback<T : Option> : DiffUtil.ItemCallback<Category<T>>() {
        override fun areItemsTheSame(oldItem: Category<T>, newItem: Category<T>): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Category<T>, newItem: Category<T>): Boolean =
            oldItem == newItem
    }
}