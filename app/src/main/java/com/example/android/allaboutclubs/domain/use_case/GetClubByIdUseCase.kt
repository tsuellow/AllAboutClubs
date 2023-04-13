package com.example.android.allaboutclubs.domain.use_case

import com.example.android.allaboutclubs.domain.model.Club
import com.example.android.allaboutclubs.domain.repository.ClubsRepository

class GetClubByIdUseCase( private  val repository: ClubsRepository) {

    suspend operator fun invoke(id:String):Club{
        return repository.getClubById(id)
    }

}