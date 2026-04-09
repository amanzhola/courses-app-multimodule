package com.example.coursesapp.feature_course.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursesapp.core.ui.UiText
import com.example.coursesapp.domain.usecase.GetCoursesUseCase
import com.example.coursesapp.domain.usecase.ToggleFavoriteUseCase
import com.example.coursesapp.feature_course.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CourseUiState(isLoading = true))
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    private var currentCourseId: Int = NO_COURSE_ID

    fun loadCourse(courseId: Int) {
        currentCourseId = courseId

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
            )

            runCatching {
                getCoursesUseCase().firstOrNull { course ->
                    course.id == courseId
                }
            }.onSuccess { course ->
                _uiState.value = CourseUiState(
                    isLoading = false,
                    course = course,
                    error = if (course == null) {
                        UiText.StringResource(R.string.error_course_not_found)
                    } else {
                        null
                    },
                )
            }.onFailure { throwable ->
                _uiState.value = CourseUiState(
                    isLoading = false,
                    course = null,
                    error = throwable.toUiText(R.string.error_course_load),
                )
            }
        }
    }

    fun toggleFavorite() {
        val course = _uiState.value.course ?: return

        viewModelScope.launch {
            runCatching {
                toggleFavoriteUseCase(course.id)
            }.onSuccess {
                loadCourse(course.id)
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    error = throwable.toUiText(R.string.error_toggle_favorite),
                )
            }
        }
    }

    fun reload() {
        if (currentCourseId != NO_COURSE_ID) {
            loadCourse(currentCourseId)
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

    private companion object {
        const val NO_COURSE_ID = -1
    }
}