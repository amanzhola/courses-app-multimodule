package com.example.coursesapp.data.local.source

import android.content.Context
import com.example.coursesapp.data.remote.dto.CoursesResponseDto
import com.google.gson.Gson

class CoursesLocalMockDataSource(
    private val context: Context,
    private val gson: Gson
) {
    fun getCourses(): CoursesResponseDto {
        val json = context.assets.open("courses.json")
            .bufferedReader()
            .use { it.readText() }

        return gson.fromJson(json, CoursesResponseDto::class.java)
    }
}