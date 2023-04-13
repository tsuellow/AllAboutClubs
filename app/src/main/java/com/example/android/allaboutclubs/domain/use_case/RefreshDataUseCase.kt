package com.example.android.allaboutclubs.domain.use_case

import com.example.android.allaboutclubs.common.Resource
import com.example.android.allaboutclubs.domain.repository.ClubsRepository
import kotlinx.coroutines.flow.Flow

class RefreshDataUseCase( private  val repository: ClubsRepository) {

    operator fun invoke () : Flow<Resource<Unit>> = repository.updateClubs()

}