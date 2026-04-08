package com.example.coursesapp.feature_profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursesapp.feature_profile.R
import com.example.coursesapp.feature_profile.databinding.FragmentProfileBinding
import com.example.coursesapp.feature_profile.presentation.adapter.ProfileCoursesAdapter
import kotlinx.coroutines.delay
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

        observeCourses()
    }

    override fun onResume() {
        super.onResume()

        binding.profileProgressBar.visibility = View.VISIBLE

        viewModel.loadCourses()

        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)
            binding.profileProgressBar.visibility = View.GONE
        }
    }

    private fun observeCourses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.courses.collect { courses ->
                binding.profileProgressBar.visibility = View.GONE
                adapter.submitList(courses)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
