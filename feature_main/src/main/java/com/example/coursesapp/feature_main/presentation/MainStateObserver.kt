package com.example.coursesapp.feature_main.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainStateObserver(
    private val fragment: Fragment,
    private val viewModel: MainViewModel,
    private val onStateChanged: (MainUiState) -> Unit,
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        fragment.viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                onStateChanged(state)
            }
        }
    }
}