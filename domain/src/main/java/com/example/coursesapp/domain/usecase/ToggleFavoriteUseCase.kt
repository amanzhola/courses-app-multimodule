package com.example.coursesapp.domain.usecase

import com.example.coursesapp.domain.repository.CoursesRepository

class ToggleFavoriteUseCase(
    private val repository: CoursesRepository,
) {
    suspend operator fun invoke(courseId: Int) {
        repository.toggleFavorite(courseId)
    }
}