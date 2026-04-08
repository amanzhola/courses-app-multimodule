package com.example.coursesapp.feature_main.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursesapp.feature_main.R
import com.example.coursesapp.feature_main.databinding.FragmentMainBinding
import com.example.coursesapp.feature_main.presentation.adapter.CoursesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var navigation: MainNavigation? = null
    private val viewModel: MainViewModel by viewModel()

    private lateinit var adapter: CoursesAdapter

    private var isNavigationInProgress = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        navigation = activity as? MainNavigation

        setupSearchHintBehavior()
        setupRecycler()
        setupListeners()
        observeState()
    }

    override fun onResume() {
        super.onResume()
        isNavigationInProgress = false
        viewModel.loadCourses()
    }

    private fun setupSearchHintBehavior() {
        binding.searchEditText.setOnFocusChangeListener { _, hasFocus ->
            binding.searchEditText.hint =
                if (hasFocus) {
                    EMPTY_TEXT
                } else {
                    getString(R.string.main_search_hint)
                }
        }
    }

    private fun setupRecycler() {
        adapter = CoursesAdapter(
            onDetailsClick = { course ->
                if (isNavigationInProgress) return@CoursesAdapter

                isNavigationInProgress = true
                navigation?.openCourseScreen(course.id)
            },
            onFavoriteClick = { course ->
                viewModel.toggleFavorite(course)
            }
        )

        binding.coursesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.coursesRecyclerView.adapter = adapter
    }

    private fun setupListeners() {
        binding.sortContainer.setOnClickListener {
            viewModel.sortByPublishDateDesc()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycle.addObserver(
            MainStateObserver(
                fragment = this,
                viewModel = viewModel,
                onStateChanged = { state ->
                    binding.mainProgressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE

                    adapter.submitList(state.courses)
                }
            )
        )
    }

    override fun onDestroyView() {
        _binding = null
        navigation = null
        super.onDestroyView()
    }

    private companion object {
        const val EMPTY_TEXT = ""
    }
}
