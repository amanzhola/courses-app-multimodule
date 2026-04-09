package com.example.coursesapp.feature_main.presentation

import com.example.coursesapp.core.ui.UiText
import com.example.coursesapp.domain.model.Course

data class MainUiState(
    val isLoading: Boolean = false,
    val courses: List<Course> = emptyList(),
    val error: UiText? = null,
)