package com.example.coursesapp.data.di

import android.app.Application
import androidx.room.Room
import com.example.coursesapp.data.BuildConfig
import com.example.coursesapp.data.local.db.AppDatabase
import com.example.coursesapp.data.local.source.CoursesLocalMockDataSource
import com.example.coursesapp.data.remote.api.CoursesApi
import com.example.coursesapp.data.repository.CoursesRepositoryImpl
import com.example.coursesapp.domain.repository.CoursesRepository
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single<CoursesApi> {
        get<Retrofit>().create(CoursesApi::class.java)
    }

    single<Gson> {
        Gson()
    }

    single<AppDatabase> {
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
}