package com.example.coursesapp.feature_main.presentation

import com.example.coursesapp.domain.model.Course

data class MainUiState(
    val isLoading: Boolean = false,
    val courses: List<Course> = emptyList(),
    val errorMessage: String? = null,
)