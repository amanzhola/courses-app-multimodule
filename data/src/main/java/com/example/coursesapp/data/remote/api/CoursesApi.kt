package com.example.coursesapp.data.remote.api

import com.example.coursesapp.data.remote.dto.CoursesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoursesApi {

    @GET("u/0/uc")
    suspend fun getCourses(
        @Query("id") id: String = MOCK_FILE_ID,
        @Query("export") export: String = "download",
    ): CoursesResponseDto

    companion object {
        const val MOCK_FILE_ID = "15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q"
    }
}