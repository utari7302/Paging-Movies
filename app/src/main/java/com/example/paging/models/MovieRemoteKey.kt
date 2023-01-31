package com.example.paging.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieRemoteKey (
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val prevPage: Int?,
    val nextPage: Int?
)