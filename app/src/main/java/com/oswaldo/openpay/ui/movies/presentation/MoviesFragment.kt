package com.oswaldo.openpay.ui.movies.presentation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.oswaldo.openpay.databinding.FragmentMoviesBinding
import com.oswaldo.openpay.ui.detail.DetailActivity
import com.oswaldo.openpay.ui.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.oswaldo.openpay.ui.movies.domain.model.Movie
import com.oswaldo.openpay.ui.movies.presentation.adapters.IMovieEvent
import com.oswaldo.openpay.ui.movies.presentation.adapters.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(), IMovieEvent {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()

    private lateinit var popularMoviesAdapter: MovieListAdapter
    private lateinit var ratedMoviesAdapter: MovieListAdapter
    private lateinit var upcomingMoviesAdapter: MovieListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeState()
        observeNavigation()
        viewModel.init()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvUpcoming.layoutManager = createHorizontalLinearLayout()
            rvTopRated.layoutManager = createHorizontalLinearLayout()
            rvRecommended.layoutManager = createHorizontalLinearLayout()
        }
    }

    private fun createHorizontalLinearLayout() = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    private fun observeState() {
        viewModel.stateLiveData.observe(requireActivity()) {
            when (it) {
                is MoviesViewModel.State.ShowData -> showData(it.popularMovies, it.topRelatedMovies, it.upcomingMovies)
                else -> {}
            }
        }
    }

    private fun observeNavigation() { }

    private fun showData(popularMovies: List<Movie>, topRelatedMovies: List<Movie>, upcomingMovies: List<Movie>) {
        binding.apply {
            popularMoviesAdapter = MovieListAdapter(popularMovies, this@MoviesFragment)
            ratedMoviesAdapter = MovieListAdapter(topRelatedMovies, this@MoviesFragment)
            upcomingMoviesAdapter = MovieListAdapter(upcomingMovies, this@MoviesFragment)

            rvUpcoming.adapter = popularMoviesAdapter
            rvTopRated.adapter = ratedMoviesAdapter
            rvRecommended.adapter = upcomingMoviesAdapter
        }
    }

    override fun onClickMovie(movie: Movie) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        requireActivity().startActivity(intent)
    }

}