package com.example.paging.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging.models.TvShow
import com.example.paging.retrofit.MovieApi

class MoviePagingSource(private val movieApi: MovieApi): PagingSource<Int,TvShow>()  {

    //Each PagingSource object defines a source of data and how to retrieve data from that source.
    // A PagingSource object can load data from any single source,
    // including network sources and local databases.


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        return try {
            val position = params.key ?: 1
            val response = movieApi.getMovies(position)

            LoadResult.Page(
                data = response.tv_shows,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if(position == response.pages) null else position + 1
            )

        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let{ anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }
    }


}