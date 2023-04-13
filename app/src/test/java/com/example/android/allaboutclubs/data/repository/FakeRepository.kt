package com.example.android.allaboutclubs.data.repository

import com.example.android.allaboutclubs.common.Resource
import com.example.android.allaboutclubs.domain.model.Club
import com.example.android.allaboutclubs.domain.repository.ClubsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository:ClubsRepository {

    private val clubs = mutableListOf<Club>()

    fun setClubList(clubsList:List<Club>){
        clubs.addAll(clubsList)
    }

    override fun updateClubs(): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getClubsList(): Flow<List<Club>> {
        return flow { emit(clubs) }
    }

    override suspend fun getClubById(id: String): Club {
        TODO("Not yet implemented")
    }
}