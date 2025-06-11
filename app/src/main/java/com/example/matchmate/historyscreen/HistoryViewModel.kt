package com.example.matchmate.historyscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetProfilesFromRoomUsecase
import com.example.domain.usecase.ProfileHistoryUsecase
import com.example.matchmate.matchscreen.ProfilesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getProfileHistoryUsecase: ProfileHistoryUsecase
)  : ViewModel(){

    private val _profilesState = MutableStateFlow(ProfilesListState())

    fun consumableState() = _profilesState.asStateFlow()

    init {
        getProfileHistory()
    }

    fun getProfileHistory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                //val curr = _profilesState.value
                getProfileHistoryUsecase.execute().collect({ profiles ->
                    _profilesState.value = _profilesState.value.copy(isLoading = false, matchProfiles = profiles)

                    Log.d("sarthak", "getProfileHistory: $profiles")
                    /*val items = curr.matchProfiles.toMutableList().apply {
                        addAll(profiles)
                    }
                    _profilesState.value = _profilesState.value.copy(matchProfiles = items)*/
                })
            }
        }
    }

}