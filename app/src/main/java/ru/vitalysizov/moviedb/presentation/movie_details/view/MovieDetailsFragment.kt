package ru.vitalysizov.moviedb.presentation.movie_details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ConcatAdapter
import ru.vitalysizov.moviedb.R
import ru.vitalysizov.moviedb.databinding.FragmentMovieDetailsBinding
import ru.vitalysizov.moviedb.presentation.base.view.BaseFragment
import ru.vitalysizov.moviedb.presentation.movie_details.adapters.backDrop.CarouselBackDropAdapter
import ru.vitalysizov.moviedb.presentation.movie_details.adapters.castAndCrew.CarouselCastAndCrewAdapter
import ru.vitalysizov.moviedb.presentation.movie_details.adapters.posterAndDescription.PosterAndDescriptionAdapter
import ru.vitalysizov.moviedb.presentation.movie_details.adapters.titleInfo.TitleAdapter
import ru.vitalysizov.moviedb.presentation.movie_details.viewmodel.MovieDetailsViewModel
import javax.inject.Inject

class MovieDetailsFragment : BaseFragment() {

    private val backDropAdapter = CarouselBackDropAdapter()
    private val titleAdapter = TitleAdapter()
    private val posterAndDescriptionAdapter = PosterAndDescriptionAdapter()
    private val castAndCrewAdapter = CarouselCastAndCrewAdapter()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMovieDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = movieDetailsViewModel

        initMovieDetailsAdapters(binding)
        initMovieDetailsDataObservers()

        return binding.root
    }

    private fun initMovieDetailsAdapters(binding: FragmentMovieDetailsBinding) {
        val concatAdapter = ConcatAdapter(
            backDropAdapter,
            titleAdapter,
            posterAndDescriptionAdapter,
            castAndCrewAdapter
        )
        binding.rvMovieDetails.adapter = concatAdapter
    }

    private fun initMovieDetailsDataObservers() {
        movieDetailsViewModel.backDropImages.observe(viewLifecycleOwner) { items ->
            items.let {
                backDropAdapter.submitList(items)
            }
        }

        movieDetailsViewModel.movieDetails.observe(viewLifecycleOwner) { items ->
            items.let {
                titleAdapter.submitList(items)
                posterAndDescriptionAdapter.submitList(items)
            }
        }

        movieDetailsViewModel.castAndCrew.observe(viewLifecycleOwner) { items ->
            items.let {
                castAndCrewAdapter.submitList(items)
            }
        }
    }
}