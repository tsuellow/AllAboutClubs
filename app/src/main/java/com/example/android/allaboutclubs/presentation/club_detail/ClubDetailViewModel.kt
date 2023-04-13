package com.example.android.allaboutclubs.presentation.club_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.allaboutclubs.domain.model.Club
import com.example.android.allaboutclubs.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubDetailViewModel @Inject constructor(
    private val useCases: UseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * The state property club is nullable in order to reflect the initial data loading stage with a null value.
     * That way we avoid using an unnecessary state variable. Since the data is retrieved from
     * the local Room DB we don't need to handle data retrieval failures as with an Api
     */
    private val _club: MutableState<Club?> = mutableStateOf<Club?>(null)
    val club: State<Club?> = _club

    init {
        savedStateHandle.get<String>("clubId")?.let { id ->

            viewModelScope.launch {
                _club.value = useCases.getClubByIdUseCase(id)
            }

        }
    }

}