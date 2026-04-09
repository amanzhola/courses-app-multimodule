package com.example.coursesapp.feature_profile.presentation

import com.example.coursesapp.core.ui.UiText
import com.example.coursesapp.feature_profile.presentation.adapter.ProfileCourseUi

data class ProfileUiState(
    val isLoading: Boolean = false,
    val courses: List<ProfileCourseUi> = emptyList(),
    val error: UiText? = null,
)