package com.example.coursesapp.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _courses = MutableStateFlow<List<ProfileCourseUi>>(emptyList())
    val courses: StateFlow<List<ProfileCourseUi>> = _courses.asStateFlow()

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            val courses = getCoursesUseCase()
            _courses.value = courses.map { it.toProfileCourseUi() }
        }
    }

    fun toggleFavorite(item: ProfileCourseUi) {
        viewModelScope.launch {
            toggleFavoriteUseCase(item.course.id)
            loadCourses()
        }
    }

    private fun Course.toProfileCourseUi(): ProfileCourseUi {
        return when (id) {
            101 -> ProfileCourseUi(
                course = this,
                progressPercent = 50,
                lessonsText = "22/44 уроков",
                coverRes = R.drawable.ic_cover2,
            )
            100 -> ProfileCourseUi(
                course = this,
                progressPercent = 30,
                lessonsText = "15/48 уроков",
                coverRes = R.drawable.ic_cover,
            )
            102 -> ProfileCourseUi(
                course = this,
                progressPercent = 70,
                lessonsText = "28/40 уроков",
                coverRes = R.drawable.ic_cover3,
            )
            103 -> ProfileCourseUi(
                course = this,
                progressPercent = 10,
                lessonsText = "4/40 уроков",
                coverRes = R.drawable.ic_cover4,
            )
            else -> ProfileCourseUi(
                course = this,
                progressPercent = 20,
                lessonsText = "8/40 уроков",
                coverRes = R.drawable.ic_cover5,
            )
        }
    }
}