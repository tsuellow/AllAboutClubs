package com.example.android.allaboutclubs.domain.use_case

import com.example.android.allaboutclubs.domain.model.Club
import com.example.android.allaboutclubs.domain.repository.ClubsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetClubsListUseCase(
    private val repository: ClubsRepository
) {

    /**
     * returns a flow of club lists ordered according to the sorting parameter
     */
    operator fun invoke(sorting: Sorting = Sorting.AlphabeticallyAsc): Flow<List<Club>> {
        return repository.getClubsList().map { clubs ->
            when(sorting){
                is Sorting.AlphabeticallyAsc -> clubs.sortedBy { it.name }
                is Sorting.ByValueDesc -> clubs.sortedByDescending { it.value }
            }
        }
    }

}

sealed class Sorting {
    object AlphabeticallyAsc : Sorting()
    object ByValueDesc : Sorting()
}