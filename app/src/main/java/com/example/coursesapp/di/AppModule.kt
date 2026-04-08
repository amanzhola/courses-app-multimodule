package com.example.coursesapp.di

import com.example.coursesapp.domain.usecase.GetCoursesUseCase
import com.example.coursesapp.domain.usecase.GetFavoriteCoursesUseCase
import com.example.coursesapp.domain.usecase.ToggleFavoriteUseCase
import com.example.coursesapp.feature_course.presentation.CourseViewModel
import com.example.coursesapp.feature_favorite.presentation.FavoriteViewModel
import com.example.coursesapp.feature_main.presentation.MainViewModel
import com.example.coursesapp.feature_profile.presentation.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { GetCoursesUseCase(get()) }
    factory { GetFavoriteCoursesUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }

    viewModel {
        MainViewModel(
            getCoursesUseCase = get(),
            toggleFavoriteUseCase = get(),
        )
    }

    viewModel {
        FavoriteViewModel(
            getFavoriteCoursesUseCase = get(),
            toggleFavoriteUseCase = get(),
        )
    }

    viewModel {
        ProfileViewModel(
            getCoursesUseCase = get(),
            toggleFavoriteUseCase = get(),
        )
    }

    viewModel {
        CourseViewModel(
            getCoursesUseCase = get(),
            toggleFavoriteUseCase = get(),
        )
    }
}
