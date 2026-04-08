package com.example.coursesapp.domain.repository

import com.example.coursesapp.domain.model.Course

interface CoursesRepository {
    suspend fun getCourses(): List<Course>
    suspend fun getFavoriteCourses(): List<Course>
    suspend fun toggleFavorite(courseId: Int)
}