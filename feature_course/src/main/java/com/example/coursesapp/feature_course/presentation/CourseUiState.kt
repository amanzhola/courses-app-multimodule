package com.example.coursesapp.feature_course.presentation

import com.example.coursesapp.domain.model.Course

data class CourseUiState(
    val isLoading: Boolean = false,
    val course: Course? = null,
    val errorMessage: String? = null,
)