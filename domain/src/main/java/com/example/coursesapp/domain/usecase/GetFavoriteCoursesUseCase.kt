package com.example.coursesapp.domain.usecase

import com.example.coursesapp.domain.model.Course
import com.example.coursesapp.domain.repository.CoursesRepository

class GetFavoriteCoursesUseCase(
    private val repository: CoursesRepository,
) {
    suspend operator fun invoke(): List<Course> = repository.getFavoriteCourses()
}