package com.example.paging

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging.db.MovieDao
import com.example.paging.db.RemoteKeyDao
import com.example.paging.models.MovieRemoteKey
import com.example.paging.models.TvShow

@Database(entities = [TvShow::class,MovieRemoteKey::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeyDao
}