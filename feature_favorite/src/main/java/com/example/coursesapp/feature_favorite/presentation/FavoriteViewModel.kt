package com.example.coursesapp.feature_favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursesapp.domain.model.Course
import com.example.coursesapp.domain.usecase.GetFavoriteCoursesUseCase
import com.example.coursesapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _courses.value = getFavoriteCoursesUseCase()
        }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch {
            toggleFavoriteUseCase(course.id)
            load()
        }
    }
}