    plugins {
        alias(libs.plugins.android.library)
        alias(libs.plugins.kotlin.android)
        alias(libs.plugins.ksp)
    }

    android {
        namespace = "com.example.coursesapp.data"
        compileSdk = 36

        defaultConfig {
            minSdk = 28
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        kotlinOptions {
            jvmTarget = "11"
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    dependencies {
        implementation(project(":domain"))

        implementation(libs.androidx.core.ktx)
        implementation(libs.kotlinx.coroutines.android)

        implementation(libs.retrofit)
        implementation(libs.retrofit.gson)
        implementation(libs.okhttp.logging)

        implementation(libs.koin.android)

        implementation(libs.androidx.room.runtime)
        implementation(libs.androidx.room.ktx)
        ksp(libs.androidx.room.compiler)

        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    }
