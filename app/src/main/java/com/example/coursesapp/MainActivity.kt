package com.example.coursesapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.coursesapp.feature_auth.presentation.enter.EnterNavigation
import com.example.coursesapp.feature_favorite.presentation.FavoriteNavigation
import com.example.coursesapp.feature_main.presentation.MainNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main),
    EnterNavigation,
    MainNavigation,
    FavoriteNavigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.itemIconTintList = null
        bottomNav.setItemTextColor(
            androidx.core.content.ContextCompat.getColorStateList(
                this,
                R.color.selector_bottom_nav
            )
        )
        bottomNav.labelVisibilityMode =
            com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_LABELED
        bottomNav.itemIconSize = (38 * resources.displayMetrics.density).toInt()
        bottomNav.setItemActiveIndicatorEnabled(false)

        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav.visibility = when (destination.id) {
                R.id.mainFragment,
                R.id.favoriteFragment,
                R.id.profileFragment,
                R.id.courseFragment -> View.VISIBLE
                else -> View.GONE
            }
        }

    }

    override fun openMainScreen() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navHostFragment.navController.navigate(R.id.mainFragment)
    }

    override fun openCourseScreen(courseId: Int) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        val bundle = Bundle().apply {
            putInt("courseId", courseId)
        }

        navHostFragment.navController.navigate(R.id.courseFragment, bundle)
    }
}