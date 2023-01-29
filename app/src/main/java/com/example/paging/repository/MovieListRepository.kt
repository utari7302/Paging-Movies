package com.example.paging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.paging.paging.MoviePagingSource
import com.example.paging.retrofit.MovieApi
import javax.inject.Inject


class MovieListRepository @Inject constructor(private val movieApi: MovieApi) {

    fun getMoviesList() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = {MoviePagingSource(movieApi)}
    ).liveData
}