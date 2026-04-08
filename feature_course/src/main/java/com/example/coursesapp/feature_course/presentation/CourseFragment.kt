package com.example.coursesapp.feature_course.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.coursesapp.feature_course.R
import com.example.coursesapp.feature_course.databinding.FragmentCourseBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseFragment : Fragment(R.layout.fragment_course) {

    private var _binding: FragmentCourseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CourseViewModel by viewModel()
    private var courseId: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCourseBinding.bind(view)

        courseId = arguments?.getInt("courseId", -1) ?: -1

        binding.backImageView.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.bookmarkImageView.setOnClickListener {
            viewModel.toggleFavorite()
        }

        observeState()

        if (courseId != -1) {
            viewModel.loadCourse(courseId)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.reload()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.courseCoverProgressBar.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                val course = state.course ?: return@collect

                binding.courseTitleTextView.text = course.title
                binding.rateTextView.text = course.rate.toString()
                binding.dateTextView.text = course.startDate

                binding.bookmarkImageView.setImageResource(
                    if (course.hasLike) {
                        R.drawable.ic_course_bookmark_green
                    } else {
                        R.drawable.ic_course_bookmark
                    }
                )

                binding.courseCoverImageView.setImageResource(
                    when (course.id) {
                        100 -> R.drawable.ic_cover
                        101 -> R.drawable.ic_cover2
                        102 -> R.drawable.ic_cover3
                        103 -> R.drawable.ic_cover4
                        else -> R.drawable.ic_cover5
                    }
                )

                binding.aboutCourseBodyTextView.text = course.text
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}