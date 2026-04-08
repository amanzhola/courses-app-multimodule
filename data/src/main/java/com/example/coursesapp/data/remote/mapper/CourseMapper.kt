package com.example.coursesapp.data.remote.mapper

import com.example.coursesapp.data.remote.dto.CourseDto
import com.example.coursesapp.domain.model.Course
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun CourseDto.toDomain(): Course {
    return Course(
        id = id,
        title = title,
        text = text,
        price = price,
        rate = rate.toDoubleOrNull() ?: 0.0,
        startDate = startDate.toUiDate(),
        hasLike = hasLike,
        publishDate = publishDate,
    )
}

private fun String.toUiDate(): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val date = LocalDate.parse(this, inputFormatter)

        val months = listOf(
            "Января", "Февраля", "Марта", "Апреля", "Мая", "Июня",
            "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"
        )

        "${date.dayOfMonth} ${months[date.monthValue - 1]} ${date.year}"
    } catch (e: Exception) {
        this
    }
}