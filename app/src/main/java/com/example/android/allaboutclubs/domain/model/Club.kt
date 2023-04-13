package com.example.android.allaboutclubs.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.allaboutclubs.data.remote.dto.LocationDto
import com.example.android.allaboutclubs.data.remote.dto.StadiumDto

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