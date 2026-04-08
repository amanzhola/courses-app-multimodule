package com.example.coursesapp.feature_main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coursesapp.domain.model.Course
import com.example.coursesapp.feature_main.R
import com.example.coursesapp.feature_main.databinding.ItemCourseBinding

class CoursesAdapter(
    private val onDetailsClick: (Course) -> Unit,
    private val onFavoriteClick: (Course) -> Unit,
) : ListAdapter<Course, CoursesAdapter.CourseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(
        private val binding: ItemCourseBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) = with(binding) {
            titleTextView.text = course.title
            descriptionTextView.text = course.text
            rateTextView.text = course.rate.toString()
            dateTextView.text = course.startDate
            priceTextView.text = "${course.price} ₽"

            val coverRes = when (course.id) {
                100 -> R.drawable.ic_cover
                101 -> R.drawable.ic_cover2
                102 -> R.drawable.ic_cover3
                103 -> R.drawable.ic_cover4
                else -> R.drawable.ic_cover5
            }

            val bookmarkRes = if (course.hasLike) {
                R.drawable.ic_bookmark_green
            } else {
                R.drawable.ic_bookmark_empty
            }

            courseImageView.setImageResource(coverRes)
            favoriteImageView.setImageResource(bookmarkRes)

            detailsContainer.setOnClickListener { onDetailsClick(course) }
            detailsTextView.setOnClickListener { onDetailsClick(course) }
            detailsArrowImageView.setOnClickListener { onDetailsClick(course) }

            favoriteImageView.setOnClickListener {
                onFavoriteClick(course)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
                return oldItem == newItem
            }
        }
    }
}