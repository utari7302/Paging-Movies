package com.example.paging.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging.models.MovieRemoteKey

@Dao
interface RemoteKeyDao {

    @Query("SELECT * FROM MovieRemoteKey WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): MovieRemoteKey

    // onConflict = OnConflictStrategy.REPLACE: Primary key with in same objects should be replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MovieRemoteKey>)

    @Query("DELETE FROM MovieRemoteKey")
    suspend fun deleteAllRemoteKeys()
}