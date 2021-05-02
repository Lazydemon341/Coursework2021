package com.avvlas.coursework2021.ui.addmacro.pages.listadapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alespero.expandablecardview.ExpandableCardView
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.model.options.Category
import com.avvlas.coursework2021.model.options.Option
import com.avvlas.coursework2021.utils.Utils
import com.google.android.material.color.MaterialColors

class CategoriesListAdapter<T : Option>(
    private val onOptionClickListener: OptionsListAdapter.OnOptionClickListener<T>
) :
    ListAdapter<Category<T>, CategoriesListAdapter<T>.CategoriesViewHolder>(DiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class CategoriesViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val optionsRecyclerView: RecyclerView = itemView.findViewById(R.id.categories_recycler_view)
        private lateinit var adapter: OptionsListAdapter<T>

        fun bind(category: Category<T>) {
            setTitleAndIcon(category)
            initRecyclerView(category)
        }

        private fun setTitleAndIcon(category: Category<T>) =
            itemView.findViewById<ExpandableCardView>(R.id.category_expandable_card_view).apply {
                setTitle(category.title)
                setIcon(
                    ContextCompat.getDrawable(
                        itemView.context,
                        category.icon
                    )?.apply {
                        // Change color of the icon to colorPrimary
                        mutate().colorFilter = PorterDuffColorFilter(
                            MaterialColors.getColor(
                                itemView,
                                R.attr.colorPrimary,
                                ContextCompat.getColor(itemView.context, R.color.white)
                            ),
                            PorterDuff.Mode.SRC_IN
                        )
                    }
                )
            }


        private fun initRecyclerView(category: Category<T>) {
            optionsRecyclerView.apply {
                optionsRecyclerView.adapter = OptionsListAdapter<T>(onOptionClickListener).also {
                    this@CategoriesViewHolder.adapter = it
                }
                addItemDecoration(
                    GridSpacingItemDecoration(
                        2,
                        Utils.dpToPx(resources, 8),
                        false
                    )
                )
            }
            adapter.submitList(category.items)
        }
    }

    class DiffCallback<T : Option> : DiffUtil.ItemCallback<Category<T>>() {
        override fun areItemsTheSame(oldItem: Category<T>, newItem: Category<T>): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Category<T>, newItem: Category<T>): Boolean =
            oldItem == newItem
    }
}