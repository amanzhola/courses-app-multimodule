pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CoursesApp"
include(
    ":app",
)

include(":domain")
include(":data")
include(":feature_onboarding")
include(":feature_auth")
include(":feature_main")
include(":feature_course")
include(":feature_favorite")
include(":feature_profile")
include(":core-ui")
