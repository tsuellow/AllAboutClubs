package com.example.android.allaboutclubs.data.remote.dto

import com.example.android.allaboutclubs.domain.model.Club

data class ClubDto(
    val country: String,
    val european_titles: Int,
    val id: String,
    val image: String,
    val location: LocationDto,
    val name: String,
    val stadium: StadiumDto,
    val value: Int
) {

    fun toClub(): Club {
        return Club(
            id = id,
            country = country,
            european_titles = european_titles,
            image = image,
            name = name,
            stadiumName = stadium.name,
            value = value
        )
    }
}