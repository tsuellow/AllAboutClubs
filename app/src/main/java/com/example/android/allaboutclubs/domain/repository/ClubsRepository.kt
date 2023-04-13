package com.example.android.allaboutclubs.domain.repository

import com.example.android.allaboutclubs.common.Resource
import com.example.android.allaboutclubs.data.remote.dto.ClubDto
import com.example.android.allaboutclubs.domain.model.Club
import kotlinx.coroutines.flow.Flow

interface ClubsRepository {

    fun updateClubs():Flow<Resource<Unit>>

    fun getClubsList(): Flow<List<Club>>

    suspend fun getClubById(id:String):Club

}