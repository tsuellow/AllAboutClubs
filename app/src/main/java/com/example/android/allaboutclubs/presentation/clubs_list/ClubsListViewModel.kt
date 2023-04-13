package com.example.android.allaboutclubs.presentation.clubs_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.allaboutclubs.common.Resource
import com.example.android.allaboutclubs.domain.model.Club
import com.example.android.allaboutclubs.domain.use_case.Sorting
import com.example.android.allaboutclubs.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ClubsListViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _listState: MutableState<ListState> = mutableStateOf<ListState>(ListState.Loading)
    val listState: State<ListState> = _listState

    private val _listClub: MutableState<List<Club>> = mutableStateOf<List<Club>>(emptyList())
    val listClub: State<List<Club>> = _listClub

    private val _sorting: MutableState<Sorting> = mutableStateOf<Sorting>(Sorting.AlphabeticallyAsc)
    val sorting: State<Sorting> = _sorting

    init {
        refreshFromApi()
        getClubsListFromDb()
    }


    private fun getClubsListFromDb() {
        useCases.getClubsListUseCase(sorting.value).onEach { changedList ->
            _listClub.value = changedList
        }.launchIn(viewModelScope)
    }

    private fun refreshFromApi() {
        useCases.refreshDataUseCase().onEach { result ->
            _listState.value = when (result) {
                is Resource.Success -> {
                    ListState.Success
                }
                is Resource.Error -> {
                    Log.d("testiando",result.message?:"")
                    if (listClub.value.isEmpty()) ListState.Failed else ListState.NotUpToDate
                }
                is Resource.Loading -> {
                    ListState.Refreshing
                }
            }

        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ClubsListEvent) {
        when (event) {
            is ClubsListEvent.ReOrderEvent -> {
                _sorting.value =
                    if (_sorting.value is Sorting.AlphabeticallyAsc) Sorting.ByValueDesc else Sorting.AlphabeticallyAsc
                getClubsListFromDb()
            }
            is ClubsListEvent.ClubClickedEvent -> {
                //handled entirely in composable
            }
        }
    }


}

sealed class ClubsListEvent {
    object ReOrderEvent : ClubsListEvent()
    data class ClubClickedEvent(val id: String) : ClubsListEvent()
}

sealed class ListState() {
    object Loading : ListState()
    object Refreshing : ListState()
    object NotUpToDate : ListState()
    object Success : ListState()
    object Failed : ListState()
}