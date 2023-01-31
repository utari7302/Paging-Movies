package com.example.paging.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging.models.TvShow

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movies")
    fun getMovies(): PagingSource<Int,TvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<TvShow>)

    @Query("DELETE FROM Movies")
    suspend fun deleteMovies()
}