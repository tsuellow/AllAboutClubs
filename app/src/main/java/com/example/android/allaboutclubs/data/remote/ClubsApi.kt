package com.example.android.allaboutclubs.data.remote

import com.example.android.allaboutclubs.data.remote.dto.ClubDto
import retrofit2.http.GET

interface ClubsApi {

    @GET("/hiring/clubs.json")
    suspend fun getClubs():List<ClubDto>

}