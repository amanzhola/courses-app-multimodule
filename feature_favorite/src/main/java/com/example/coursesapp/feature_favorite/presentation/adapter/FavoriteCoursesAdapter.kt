package com.example.coursesapp.feature_favorite.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coursesapp.domain.model.Course
import com.example.coursesapp.feature_favorite.R
import com.example.coursesapp.feature_favorite.databinding.ItemCourseBinding

class FavoriteCoursesAdapter(
    private val onDetailsClick: (Course) -> Unit,
    private val onFavoriteClick: (Course) -> Unit
) : ListAdapter<Course, FavoriteCoursesAdapter.CourseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(
        private val binding: ItemCourseBinding
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
                else -> R.drawable.ic_cover3
            }

            courseImageView.setImageResource(coverRes)
            favoriteImageView.setImageResource(R.drawable.ic_bookmark_green)

            detailsContainer.setOnClickListener { onDetailsClick(course) }
            detailsTextView.setOnClickListener { onDetailsClick(course) }
            detailsArrowImageView.setOnClickListener { onDetailsClick(course) }

            favoriteImageView.setOnClickListener { onFavoriteClick(course) }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean =
                oldItem == newItem
        }
    }
}