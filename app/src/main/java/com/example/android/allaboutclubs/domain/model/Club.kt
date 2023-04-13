package com.example.android.allaboutclubs.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Club (
    @PrimaryKey
    val id: String,
    val country: String,
    val european_titles: Int,
    val image: String,
    val name: String,
    val stadiumName: String,
    val value: Int
)