package com.example.coursesapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoursesResponseDto(
    @SerializedName("courses")
    val courses: List<CourseDto>,
)