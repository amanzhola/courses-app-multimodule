package com.example.coursesapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coursesapp.data.local.dao.FavoriteCoursesDao
import com.example.coursesapp.data.local.entity.FavoriteCourseEntity

@Database(
    entities = [FavoriteCourseEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteCoursesDao
}