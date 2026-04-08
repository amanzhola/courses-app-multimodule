package com.example.coursesapp.feature_course.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursesapp.domain.usecase.GetCoursesUseCase
import com.example.coursesapp.domain.usecase.ToggleFavoriteUseCase
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
                errorMessage = null,
            )

            runCatching {
                getCoursesUseCase().firstOrNull { course ->
                    course.id == courseId
                }
            }.onSuccess { course ->
                _uiState.value = CourseUiState(
                    isLoading = false,
                    course = course,
                    errorMessage = if (course == null) ERROR_COURSE_NOT_FOUND else null,
                )
            }.onFailure { error ->
                _uiState.value = CourseUiState(
                    isLoading = false,
                    course = null,
                    errorMessage = error.message ?: ERROR_COURSE_LOAD,
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
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = error.message ?: ERROR_TOGGLE_FAVORITE,
                )
            }
        }
    }

    fun reload() {
        if (currentCourseId != NO_COURSE_ID) {
            loadCourse(currentCourseId)
        }
    }

    private companion object {
        const val NO_COURSE_ID = -1

        const val ERROR_COURSE_NOT_FOUND = "Курс не найден"
        const val ERROR_COURSE_LOAD = "Ошибка загрузки курса"
        const val ERROR_TOGGLE_FAVORITE = "Ошибка обновления избранного"
    }
}