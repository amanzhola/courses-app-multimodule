package com.example.coursesapp.feature_profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursesapp.core.ui.UiText
import com.example.coursesapp.core.ui.asString
import com.example.coursesapp.feature_profile.R
import com.example.coursesapp.feature_profile.databinding.FragmentProfileBinding
import com.example.coursesapp.feature_profile.presentation.adapter.ProfileCoursesAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProfileCoursesAdapter
    private val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        adapter = ProfileCoursesAdapter(
            onFavoriteClick = { item ->
                viewModel.toggleFavorite(item)
            }
        )

        binding.profileCoursesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        binding.profileCoursesRecyclerView.adapter = adapter

        observeState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCourses()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.profileProgressBar.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                adapter.submitList(state.courses)

                state.error?.let { error: UiText ->
                    Snackbar
                        .make(
                            binding.root,
                            error.asString(requireContext()),
                            Snackbar.LENGTH_SHORT
                        )
                        .show()

                    viewModel.clearError()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}