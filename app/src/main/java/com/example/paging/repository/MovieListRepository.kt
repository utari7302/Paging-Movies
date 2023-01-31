package com.example.paging.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.paging.MovieDatabase
import com.example.paging.paging.MoviePagingSource
import com.example.paging.paging.MovieRemoteMediator
import com.example.paging.retrofit.MovieApi
import javax.inject.Inject

@ExperimentalPagingApi
class MovieListRepository @Inject constructor(private val movieApi: MovieApi,private val movieDatabase: MovieDatabase) {

    fun getMoviesList() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator = MovieRemoteMediator(movieApi,movieDatabase),
        pagingSourceFactory = {movieDatabase.movieDao().getMovies()}
    ).liveData

    // Without Mediator
//    fun getMoviesList() = Pager(
//        config = PagingConfig(pageSize = 20, maxSize = 100),
//        pagingSourceFactory = {MoviePagingSource(movieApi)}
//    ).liveData
}