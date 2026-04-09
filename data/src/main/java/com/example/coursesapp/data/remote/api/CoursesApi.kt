package com.example.coursesapp.data.remote.api

import com.example.coursesapp.data.BuildConfig
import com.example.coursesapp.data.remote.dto.CoursesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoursesApi {

    @GET("u/0/uc")
    suspend fun getCourses(
        @Query("id") id: String = BuildConfig.MOCK_FILE_ID,
        @Query("export") export: String = "download",
    ): CoursesResponseDto
}