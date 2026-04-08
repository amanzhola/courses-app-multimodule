package com.example.coursesapp.feature_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursesapp.domain.model.Course
import com.example.coursesapp.domain.usecase.GetCoursesUseCase
import com.example.coursesapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState(isLoading = true))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var currentCourses: List<Course> = emptyList()
    private var isSortedByPublishDateDesc: Boolean = false

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
            )

            runCatching {
                getCoursesUseCase()
            }.onSuccess { courses ->
                currentCourses = applyCurrentSort(courses)

                _uiState.value = MainUiState(
                    isLoading = false,
                    courses = currentCourses,
                    errorMessage = null,
                )
            }.onFailure { error ->
                _uiState.value = MainUiState(
                    isLoading = false,
                    courses = emptyList(),
                    errorMessage = error.message ?: ERROR_LOAD_COURSES,
                )
            }
        }
    }

    fun sortByPublishDateDesc() {
        isSortedByPublishDateDesc = true
        currentCourses = currentCourses.sortedByDescending { it.publishDate }
        _uiState.value = _uiState.value.copy(courses = currentCourses)
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch {
            runCatching {
                toggleFavoriteUseCase(course.id)
            }.onSuccess {
                val updatedCourses = currentCourses.map { item ->
                    if (item.id == course.id) {
                        item.copy(hasLike = !item.hasLike)
                    } else {
                        item
                    }
                }

                currentCourses = applyCurrentSort(updatedCourses)
                _uiState.value = _uiState.value.copy(courses = currentCourses)
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = error.message ?: ERROR_TOGGLE_FAVORITE,
                )
            }
        }
    }

    private fun applyCurrentSort(courses: List<Course>): List<Course> {
        return if (isSortedByPublishDateDesc) {
            courses.sortedByDescending { it.publishDate }
        } else {
            courses
        }
    }

    private companion object {
        const val ERROR_LOAD_COURSES = "Ошибка загрузки курсов"
        const val ERROR_TOGGLE_FAVORITE = "Ошибка обновления избранного"
    }
}