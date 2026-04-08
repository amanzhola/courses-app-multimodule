package com.example.coursesapp.data.repository

import com.example.coursesapp.data.local.dao.FavoriteCoursesDao
import com.example.coursesapp.data.local.entity.FavoriteCourseEntity
import com.example.coursesapp.data.local.source.CoursesLocalMockDataSource
import com.example.coursesapp.data.remote.api.CoursesApi
import com.example.coursesapp.data.remote.mapper.toDomain
import com.example.coursesapp.domain.model.Course
import com.example.coursesapp.domain.repository.CoursesRepository

class CoursesRepositoryImpl(
    private val api: CoursesApi,
    private val mockDataSource: CoursesLocalMockDataSource,
    private val favoriteDao: FavoriteCoursesDao
) : CoursesRepository {

    override suspend fun getCourses(): List<Course> {
        val localOverrides = favoriteDao.getAll().associateBy { it.courseId }

        val remoteCourses = runCatching {
            api.getCourses().courses
        }.getOrElse {
            mockDataSource.getCourses().courses
        }

        return remoteCourses.map { dto ->
            val course = dto.toDomain()
            val localState = localOverrides[course.id]

            if (localState != null) {
                course.copy(hasLike = localState.isFavorite)
            } else {
                course
            }
        }
    }

    override suspend fun getFavoriteCourses(): List<Course> {
        return getCourses().filter { it.hasLike }
    }

    override suspend fun toggleFavorite(courseId: Int) {
        val courses = getCourses()
        val currentCourse = courses.firstOrNull { it.id == courseId } ?: return

        favoriteDao.insert(
            FavoriteCourseEntity(
                courseId = courseId,
                isFavorite = !currentCourse.hasLike
            )
        )
    }
}