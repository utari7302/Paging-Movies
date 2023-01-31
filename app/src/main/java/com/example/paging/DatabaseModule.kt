package com.example.paging

import android.content.Context
import androidx.room.Room
import com.example.paging.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context): MovieDatabase{
        return Room.databaseBuilder(context,MovieDatabase::class.java,Constants.DB_NAME).build()
    }
}