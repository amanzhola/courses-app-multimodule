package com.example.coursesapp.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursesapp.core.ui.UiText
import com.example.coursesapp.domain.model.Course
import com.example.coursesapp.domain.usecase.GetCoursesUseCase
import com.example.coursesapp.domain.usecase.ToggleFavoriteUseCase
import com.example.coursesapp.feature_profile.R
import com.example.coursesapp.feature_profile.presentation.adapter.ProfileCourseUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState(isLoading = true))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
            )

            runCatching {
                getCoursesUseCase()
            }.onSuccess { courses ->
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    courses = courses.map { it.toProfileCourseUi() },
                    error = null,
                )
            }.onFailure { error ->
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    courses = emptyList(),
                    error = error.toUiText(R.string.error_load_courses),
                )
            }
        }
    }

    fun toggleFavorite(item: ProfileCourseUi) {
        viewModelScope.launch {
            runCatching {
                toggleFavoriteUseCase(item.course.id)
            }.onSuccess {
                loadCourses()
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    error = error.toUiText(R.string.error_toggle_favorite),
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun Throwable.toUiText(fallbackResId: Int): UiText {
        val msg = message?.trim()
        return if (msg.isNullOrEmpty()) {
            UiText.StringResource(fallbackResId)
        } else {
            UiText.DynamicString(msg)
        }
    }

    private fun Course.toProfileCourseUi(): ProfileCourseUi {
        return when (id) {
            101 -> ProfileCourseUi(
                course = this,
                progressPercent = 50,
                lessonsTextRes = R.string.lessons_22_44,
                coverRes = R.drawable.ic_cover2,
            )
            100 -> ProfileCourseUi(
                course = this,
                progressPercent = 30,
                lessonsTextRes = R.string.lessons_15_48,
                coverRes = R.drawable.ic_cover,
            )
            102 -> ProfileCourseUi(
                course = this,
                progressPercent = 70,
                lessonsTextRes = R.string.lessons_28_40,
                coverRes = R.drawable.ic_cover3,
            )
            103 -> ProfileCourseUi(
                course = this,
                progressPercent = 10,
                lessonsTextRes = R.string.lessons_4_40,
                coverRes = R.drawable.ic_cover4,
            )
            else -> ProfileCourseUi(
                course = this,
                progressPercent = 20,
                lessonsTextRes = R.string.lessons_8_40,
                coverRes = R.drawable.ic_cover5,
            )
        }
    }
}