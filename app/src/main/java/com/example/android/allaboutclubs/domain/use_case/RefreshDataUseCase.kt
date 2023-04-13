package com.example.android.allaboutclubs.domain.use_case

import com.example.android.allaboutclubs.common.Resource
import com.example.android.allaboutclubs.domain.repository.ClubsRepository
import kotlinx.coroutines.flow.Flow

class RefreshDataUseCase( private  val repository: ClubsRepository) {
    /**
     * prompts a refresh of the local database with API data and returns a flow of type Resource
     * that indicates whether the data was refreshed successfully
     */
    operator fun invoke () : Flow<Resource<Unit>> = repository.updateClubs()

}