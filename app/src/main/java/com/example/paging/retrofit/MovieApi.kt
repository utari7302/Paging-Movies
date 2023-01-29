package com.example.paging.retrofit

import com.example.paging.models.MovieList
import com.example.paging.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET(Constants.MOST_POPULAR)
    suspend fun getMovies(@Query("page") page: Int):MovieList
}