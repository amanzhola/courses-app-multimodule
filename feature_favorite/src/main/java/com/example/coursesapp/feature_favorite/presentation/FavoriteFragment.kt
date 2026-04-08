package com.example.coursesapp.feature_favorite.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursesapp.feature_favorite.R
import com.example.coursesapp.feature_favorite.databinding.FragmentFavoriteBinding
import com.example.coursesapp.feature_favorite.presentation.adapter.FavoriteCoursesAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private var navigation: FavoriteNavigation? = null
    private lateinit var adapter: FavoriteCoursesAdapter
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteBinding.bind(view)

        navigation = activity as? FavoriteNavigation

        adapter = FavoriteCoursesAdapter(
            onDetailsClick = { course ->
                navigation?.openCourseScreen(course.id)
            },
            onFavoriteClick = { course ->
                viewModel.toggleFavorite(course)
            }
        )

        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favoriteRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.courses.collect { courses ->
                adapter.submitList(courses)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    override fun onDestroyView() {
        _binding = null
        navigation = null
        super.onDestroyView()
    }
}