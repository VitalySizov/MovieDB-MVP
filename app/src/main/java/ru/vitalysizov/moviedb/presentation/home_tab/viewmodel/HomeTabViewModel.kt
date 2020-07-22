package ru.vitalysizov.moviedb.presentation.home_tab.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.functions.Function5
import ru.vitalysizov.moviedb.R
import ru.vitalysizov.moviedb.domain.useCase.genres.LoadMoviesGenresUseCase
import ru.vitalysizov.moviedb.domain.useCase.movies.LoadNowPlayingMoviesUseCase
import ru.vitalysizov.moviedb.domain.useCase.movies.LoadPopularMoviesUseCase
import ru.vitalysizov.moviedb.domain.useCase.movies.LoadTrendingMoviesUseCase
import ru.vitalysizov.moviedb.model.domain.GenreItem
import ru.vitalysizov.moviedb.model.domain.movies.MovieItem
import ru.vitalysizov.moviedb.presentation.base.viewmodel.BaseViewModel
import ru.vitalysizov.moviedb.presentation.home_tab.adapters.models.GenresCategory
import ru.vitalysizov.moviedb.presentation.home_tab.adapters.models.MoviesCategory
import ru.vitalysizov.moviedb.utils.Event
import ru.vitalysizov.moviedb.utils.ioToUi
import javax.inject.Inject

class HomeTabViewModel @Inject constructor(
    private val loadNowPlayingMoviesUseCase: LoadNowPlayingMoviesUseCase,
    private val loadPopularMoviesUseCase: LoadPopularMoviesUseCase,
    private val loadMoviesGenresUseCase: LoadMoviesGenresUseCase,
    private val loadTrendingMoviesUseCase: LoadTrendingMoviesUseCase
) : BaseViewModel() {

    val inTheatersMoviesCategory = MutableLiveData<List<MoviesCategory>>()
    val popMoviesCategory = MutableLiveData<List<MoviesCategory>>()
    val genresCategory = MutableLiveData<List<GenresCategory>>()
    val moviesTrendingCategory = MutableLiveData<List<MoviesCategory>>()

    private val _click = MutableLiveData<Event<Int>>()
    val click: LiveData<Event<Int>>
        get() = _click

    init {
        showLoading()
        loadHomeTabContent()
    }

    companion object {
        const val TIME_PARAMS_WEEK = "week"
        const val TIME_PARAMS_DAY = "day"
    }

    private fun loadHomeTabContent() {
        launch {
            Single.zip(
                loadNowPlayingMoviesUseCase.invoke().ioToUi(),
                loadPopularMoviesUseCase.invoke().ioToUi(),
                loadTrendingMoviesUseCase.invoke(TIME_PARAMS_WEEK).ioToUi(),
                loadTrendingMoviesUseCase.invoke(TIME_PARAMS_DAY).ioToUi(),
                loadMoviesGenresUseCase.invoke().ioToUi(),
                Function5() { nowPlayingMovies: List<MovieItem>,
                              popularMovies: List<MovieItem>,
                              trendingMoviesByDay: List<MovieItem>,
                              trendingMoviesByWeek: List<MovieItem>,
                              genres: List<GenreItem> ->
                    return@Function5 HomeTabContent(
                        nowPlayingMovies,
                        popularMovies,
                        trendingMoviesByDay,
                        trendingMoviesByWeek,
                        genres
                    )
                }).subscribe({
                hideLoading()
                handleSuccessLoadHomeTabContent(it)
            }, {
                hideLoading()
                handleError(it)
            })
        }
    }

    private fun handleSuccessLoadHomeTabContent(homeTabContent: HomeTabContent) {
        inTheatersMoviesCategory.value = listOf(
            MoviesCategory(
                title = R.string.now_in_theaters_header,
                movies = homeTabContent.nowPlayingMovies
            )
        )

        popMoviesCategory.value = listOf(
            MoviesCategory(
                title = R.string.populars_header,
                movies = homeTabContent.popularMovies
            )
        )

        genresCategory.value = listOf(
            GenresCategory(homeTabContent.genres)
        )

        moviesTrendingCategory.value = listOf(
            MoviesCategory(
                R.string.populars_header,
                homeTabContent.trendingMoviesByDay
            )
        )
    }

    fun actionDetails(movieId: Int) {
        _click.value = Event(movieId)
    }
}

data class HomeTabContent(
    val nowPlayingMovies: List<MovieItem>,
    val popularMovies: List<MovieItem>,
    val trendingMoviesByDay: List<MovieItem>,
    val trendingMoviesByWeek: List<MovieItem>,
    val genres: List<GenreItem>
)