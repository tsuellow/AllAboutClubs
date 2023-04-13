package com.example.android.allaboutclubs.data.remote.repository

import com.example.android.allaboutclubs.common.Resource
import com.example.android.allaboutclubs.data.local.ClubDao
import com.example.android.allaboutclubs.data.remote.ClubsApi
import com.example.android.allaboutclubs.data.remote.dto.ClubDto
import com.example.android.allaboutclubs.domain.model.Club
import com.example.android.allaboutclubs.domain.repository.ClubsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ClubsRepositoryImpl constructor(
    private val clubDao: ClubDao,
    private val clubsApi: ClubsApi
) : ClubsRepository {

    override fun updateClubs(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val freshClubs = clubsApi.getClubs().map { it.toClub() }
            clubDao.insertClubs(freshClubs)
            emit(Resource.Success(Unit))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message?:"unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message?:"server unreachable"))
        }
    }

    override fun getClubsList(): Flow<List<Club>> {
        return clubDao.getClubs()
    }

    override suspend fun getClubById(id: String): Club {
        return clubDao.getClubById(id)
    }
}