package com.example.coursesapp.feature_profile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coursesapp.domain.model.Course
import com.example.coursesapp.feature_profile.R
import com.example.coursesapp.feature_profile.databinding.ItemProfileCourseBinding

data class ProfileCourseUi(
    val course: Course,
    val progressPercent: Int,
    val lessonsText: String,
    val coverRes: Int,
)

class ProfileCoursesAdapter(
    private val onFavoriteClick: (ProfileCourseUi) -> Unit,
) : ListAdapter<ProfileCourseUi, ProfileCoursesAdapter.ProfileCourseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileCourseViewHolder {
        val binding = ItemProfileCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return ProfileCourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileCourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProfileCourseViewHolder(
        private val binding: ItemProfileCourseBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileCourseUi) = with(binding) {
            titleTextView.text = item.course.title
            rateTextView.text = item.course.rate.toString()
            dateTextView.text = item.course.startDate
            progressPercentTextView.text = "${item.progressPercent}%"
            progressLessonsTextView.text = item.lessonsText
            progressBar.progress = item.progressPercent
            courseImageView.setImageResource(item.coverRes)

            val bookmarkRes = if (item.course.hasLike) {
                R.drawable.ic_bookmark_green
            } else {
                R.drawable.ic_bookmark_empty
            }

            favoriteImageView.setImageResource(bookmarkRes)

            favoriteImageView.setOnClickListener {
                onFavoriteClick(item)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ProfileCourseUi>() {
            override fun areItemsTheSame(oldItem: ProfileCourseUi, newItem: ProfileCourseUi): Boolean {
                return oldItem.course.id == newItem.course.id
            }

            override fun areContentsTheSame(oldItem: ProfileCourseUi, newItem: ProfileCourseUi): Boolean {
                return oldItem == newItem
            }
        }
    }
}