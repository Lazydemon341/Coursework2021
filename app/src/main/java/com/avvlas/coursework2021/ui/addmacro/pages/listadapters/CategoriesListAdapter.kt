package com.avvlas.coursework2021.ui.addmacro.pages.listadapters

import android.animation.ValueAnimator
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avvlas.coursework2021.R
import com.avvlas.coursework2021.domain.model.options.Category
import com.avvlas.coursework2021.domain.model.options.Option
import com.avvlas.coursework2021.utils.Utils
import com.google.android.material.color.MaterialColors
import kotlin.properties.Delegates

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

        private val titleTextView: TextView = itemView.findViewById(R.id.category_title_text)
        private val optionsRecyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view)
        private lateinit var adapter: OptionsListAdapter<T>

        private var originalHeight = 0
        private var expandedHeight = 0
        private var expanded = false

        fun bind(category: Category<T>) {
            setTitleAndIcon(category)
            initRecyclerView(category)

            getCollapsedAndExpandedHeights()

            itemView.setOnClickListener {
                if (expanded)
                    collapseCategory()
                else
                    expandCategory()
                expanded = !expanded
            }
        }

        private fun setTitleAndIcon(category: Category<T>) {
            titleTextView.text = category.title
            titleTextView.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    itemView.context,
                    category.icon
                )?.apply {
                    mutate().colorFilter = PorterDuffColorFilter(
                        MaterialColors.getColor(
                            itemView,
                            R.attr.colorPrimary,
                            ContextCompat.getColor(itemView.context, R.color.white)
                        ),
                        PorterDuff.Mode.SRC_IN
                    )
                }, null, null, null
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

        private fun getCollapsedAndExpandedHeights() {
            itemView.doOnLayout { view ->
                originalHeight = view.height

                // show expandView and record expandedHeight in next
                // layout pass (doOnNextLayout) and hide it immediately
                optionsRecyclerView.isVisible = true
                itemView.doOnNextLayout {
                    expandedHeight = it.height

                    // We use post{} to hide the view. Otherwise the parent will not
                    // measure itt again, since this block is done on the layout pass
                    optionsRecyclerView.post { optionsRecyclerView.isVisible = false }
                }
            }
        }

        private fun expandCategory() {
            val animator = ValueAnimator.ofInt(originalHeight, expandedHeight)
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.duration = EXPAND_DURATION
            animator.addUpdateListener {
                itemView.layoutParams.height = it.animatedValue as Int

                itemView.requestLayout()
            }
            animator.doOnEnd { optionsRecyclerView.isVisible = true }

            animator.start()
        }

        private fun collapseCategory() {
            val animator = ValueAnimator.ofInt(expandedHeight, originalHeight)
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.duration = EXPAND_DURATION
            animator.addUpdateListener {
                itemView.layoutParams.height = it.animatedValue as Int

                itemView.requestLayout()
            }
            animator.doOnStart { optionsRecyclerView.isVisible = true }

            animator.start()
        }
    }

    class DiffCallback<T : Option> : DiffUtil.ItemCallback<Category<T>>() {
        override fun areItemsTheSame(oldItem: Category<T>, newItem: Category<T>): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Category<T>, newItem: Category<T>): Boolean =
            oldItem == newItem
    }


    companion object {
        private const val EXPAND_DURATION = 200L
    }
}