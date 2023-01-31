package com.example.paging.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging.MovieDatabase
import com.example.paging.models.MovieRemoteKey
import com.example.paging.models.TvShow
import com.example.paging.retrofit.MovieApi

@ExperimentalPagingApi
class MovieRemoteMediator(private val movieApi: MovieApi,private val movieDatabase: MovieDatabase):RemoteMediator<Int,TvShow>() {

    val movieDao = movieDatabase.movieDao()
    val movieRemoteKeyDao = movieDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, TvShow>): MediatorResult {
        return try {
            val currentPage = when(loadType){
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = movieApi.getMovies(currentPage)

            val endOfPaginationReached = response.pages == currentPage
            val prevPage = if(currentPage == 1) null else currentPage - 1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if(loadType == LoadType.REFRESH){
                    movieDao.deleteMovies()
                    movieRemoteKeyDao.deleteAllRemoteKeys()
                }

                movieDao.addMovies(response.tv_shows)
                val keys = response.tv_shows.map { movie ->
                    MovieRemoteKey(
                        id = movie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                movieRemoteKeyDao.addAllRemoteKeys(keys)
            }

            MediatorResult.Success(endOfPaginationReached)
        }catch (e: Exception){
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TvShow>
    ): MovieRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieRemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, TvShow>
    ): MovieRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movieRemoteKeyDao.getRemoteKeys(id = movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, TvShow>
    ): MovieRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movieRemoteKeyDao.getRemoteKeys(id = movie.id)
            }
    }
}