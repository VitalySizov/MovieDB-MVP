package ru.vitalysizov.moviedb.data.repo

import io.reactivex.Single
import ru.vitalysizov.moviedb.model.network.responses.base.BaseResponse
import ru.vitalysizov.moviedb.model.network.responses.movies.MovieItemResponse

interface ITrendingRepository {

    fun loadTrending(
        mediaType: String,
        timeWindow: String
    ): Single<BaseResponse<MovieItemResponse>>
}