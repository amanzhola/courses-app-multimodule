package com.example.coursesapp.data.di

import android.app.Application
import androidx.room.Room
import com.example.coursesapp.data.local.db.AppDatabase
import com.example.coursesapp.data.local.source.CoursesLocalMockDataSource
import com.example.coursesapp.data.remote.api.CoursesApi
import com.example.coursesapp.data.repository.CoursesRepositoryImpl
import com.example.coursesapp.domain.repository.CoursesRepository
import com.example.coursesapp.domain.usecase.GetCoursesUseCase
import com.example.coursesapp.domain.usecase.GetFavoriteCoursesUseCase
import com.example.coursesapp.domain.usecase.ToggleFavoriteUseCase
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<CoursesApi> {
        get<Retrofit>().create(CoursesApi::class.java)
    }

    single {
        Gson()
    }

    single {
        Room.databaseBuilder(
            get<Application>(),
            AppDatabase::class.java,
            "courses_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().favoriteDao()
    }

    single {
        CoursesLocalMockDataSource(
            context = get(),
            gson = get()
        )
    }

    single<CoursesRepository> {
        CoursesRepositoryImpl(
            api = get(),
            mockDataSource = get(),
            favoriteDao = get()
        )
    }

    factory {
        GetCoursesUseCase(repository = get())
    }

    factory {
        GetFavoriteCoursesUseCase(repository = get())
    }

    factory {
        ToggleFavoriteUseCase(repository = get())
    }
}