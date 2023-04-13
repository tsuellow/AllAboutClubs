package com.example.android.allaboutclubs.presentation.clubs_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.allaboutclubs.common.PreferenceManager
import com.example.android.allaboutclubs.common.Resource
import com.example.android.allaboutclubs.common.toDateString
import com.example.android.allaboutclubs.domain.model.Club
import com.example.android.allaboutclubs.domain.use_case.Sorting
import com.example.android.allaboutclubs.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ClubsListViewModel @Inject constructor(
    private val useCases: UseCases,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _listState: MutableState<ListState> = mutableStateOf<ListState>(ListState.Loading)
    val listState: State<ListState> = _listState

    private val _listClub: MutableState<List<Club>> = mutableStateOf<List<Club>>(emptyList())
    val listClub: State<List<Club>> = _listClub

    private val _sorting: MutableState<Sorting> = mutableStateOf<Sorting>(Sorting.AlphabeticallyAsc)
    val sorting: State<Sorting> = _sorting

    var lastUpdated = mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            lastUpdated.value=preferenceManager.getLastUpdated()
            Log.d("testiando",lastUpdated.value)
            refreshFromApi()
            getClubsListFromDb()
        }

    }

    private suspend fun getClubsListFromDb() {
        useCases.getClubsListUseCase(sorting.value).collect { changedList ->
            _listClub.value = changedList
        }
    }

    private suspend fun refreshFromApi() {
        useCases.refreshDataUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _listState.value = ListState.Success
                    lastUpdated.value=Date().toDateString()
                    preferenceManager.saveDateTag(lastUpdated.value)
                }
                is Resource.Error -> {
                    _listState.value = if (lastUpdated.value.isEmpty()) {
                        ListState.Failed(msg = result.message?:"UNKNOWN ERROR")
                    }else {
                        ListState.NotUpToDate(msg = result.message?:"UNKNOWN ERROR")
                    }
                }
                is Resource.Loading -> {
                    _listState.value = ListState.Refreshing
                }
            }

        }
    }

    fun onEvent(event: ClubsListEvent) {
        when (event) {
            is ClubsListEvent.ReOrderEvent -> {
                _sorting.value =
                    if (_sorting.value is Sorting.AlphabeticallyAsc) Sorting.ByValueDesc else Sorting.AlphabeticallyAsc
                viewModelScope.launch { getClubsListFromDb() }
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
    data class NotUpToDate (val msg:String="") : ListState()
    data class Failed (val msg:String="") : ListState()
    object Success : ListState()
}