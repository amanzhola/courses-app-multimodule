package com.example.coursesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coursesapp.data.local.entity.FavoriteCourseEntity

@Dao
interface FavoriteCoursesDao {

    @Query("SELECT * FROM favorite_courses")
    suspend fun getAll(): List<FavoriteCourseEntity>

    @Query("SELECT * FROM favorite_courses WHERE courseId = :id LIMIT 1")
    suspend fun getById(id: Int): FavoriteCourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteCourseEntity)
}