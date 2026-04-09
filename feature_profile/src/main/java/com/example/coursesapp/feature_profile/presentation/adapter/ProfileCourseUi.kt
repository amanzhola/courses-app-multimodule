package com.example.coursesapp.feature_profile.presentation.adapter

import androidx.annotation.StringRes
import com.example.coursesapp.domain.model.Course

data class ProfileCourseUi(
    val course: Course,
    val progressPercent: Int,
    @StringRes val lessonsTextRes: Int,
    val coverRes: Int,
)