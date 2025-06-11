package com.example.matchmate.matchscreen

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.room.ProfilesEntity
import com.example.domain.usecase.UpdateProfileUsecase
import com.example.domain.usecase.FetchProfilesUsecase
import com.example.domain.usecase.GetProfilesFromRoomUsecase
import com.example.example.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MatchProfilesViewModel @Inject constructor(
    private val fetchProfilesUseCase: FetchProfilesUsecase,
    private val updateProfileUsecase: UpdateProfileUsecase,
    private val getProfilesFromRoomUsecase: GetProfilesFromRoomUsecase,
    @ApplicationContext context: Context
) : ViewModel() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /*private val _uiState: MutableStateFlow<ProfilesStates> =
        MutableStateFlow(value = ProfilesStates.Ideal)
    val uiState: StateFlow<ProfilesStates> = _uiState.asStateFlow()*/

    private val _profilesState = MutableStateFlow(ProfilesListState())

    fun consumableState() = _profilesState.asStateFlow()

    init {
        fetchMatchProfiles()
    }

    fun fetchMatchProfiles() {
        if (connectivityManager.activeNetwork == null){
            fetchProfilesFromRoom()
        } else {
            viewModelScope.launch {
                fetchProfilesUseCase.execute({},{}).catch { e ->
                    //uiState.value = ProfilesStates.Error(message = e.message.orEmpty())
                    Log.d("sarthak", "fetchMatchProfiles: ${e.message}")
                }.collect{ profiles ->
                    //flowlist = profiles.toMutableList()

                    //_uiState.value = ProfilesStates.Success(matchProfileList = profiles.toMutableList())

                    /*val profileMatches = profiles.map { profiles ->
                        ProfilesEntity(
                            userId = profiles.login.uuid,
                            name = "${profiles.name.title} ${profiles.name.first} ${profiles.name.last}" ,
                            profilePicUrl = profiles.picture.medium,
                            gender = profiles.gender,
                            city = "${profiles.location.city}, ${profiles.location.state} "
                        )
                    }*/
                    _profilesState.value = _profilesState.value.copy(isLoading = false, matchProfiles = profiles)
                }

            }
        }
        Log.d("sarthak", "fetchMatchProfiles:VM ")

    }

    private fun fetchProfilesFromRoom() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val curr = _profilesState.value
                getProfilesFromRoomUsecase.execute().collect({ profiles ->
                    Log.d("sarthak", "fetchProfilesFromRoom: $profiles")
                    val items = curr.matchProfiles.toMutableList().apply {
                        addAll(profiles)
                    }
                    _profilesState.value = _profilesState.value.copy(matchProfiles = items)
                })
            }
        }
    }



    fun removeProfile(profile: ProfilesEntity,status : Int) {
        val curr = _profilesState.value
        val items = curr.matchProfiles.toMutableList().apply {
            remove(profile)
        } .toList()
        _profilesState.value = _profilesState.value.copy(matchProfiles = items)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                updateProfileUsecase.execute(profile.userId, status = status)
            }
        }

    }

    fun addNewProfiles(){
        Log.d("sarthak", "addNewProfiles: ")
        if(connectivityManager.activeNetwork != null){
            viewModelScope.launch {
                val curr = _profilesState.value

                fetchProfilesUseCase.execute({},{}).catch { e ->

                }.collect{ profiles ->
                    /*val profileMatches = profiles.map { profiles ->
                        ProfilesEntity(
                            userId = profiles.login.uuid,
                            name = "${profiles.name.title} ${profiles.name.first} ${profiles.name.last}" ,
                            profilePicUrl = profiles.picture.medium,
                            gender = profiles.gender,
                            city = "${profiles.location.city}, ${profiles.location.state} "
                        )
                    }*/
                    Log.d("sarthak", "addNewProfiles:1 ${curr.matchProfiles.size}")
                    val items = curr.matchProfiles.toMutableList().apply {
                        addAll(profiles)
                    }
                    _profilesState.value = _profilesState.value.copy(matchProfiles = items)
                    Log.d("sarthak", "addNewProfiles:1 ${curr.matchProfiles.size}")

                }

            }
        }

    }

}

data class ProfilesListState(val isLoading : Boolean = true, val matchProfiles : List<ProfilesEntity> = emptyList(), val error : String? = null)

sealed class MatchProfilesListEvent{
    data class RemoveItem(val profile : Results) : MatchProfilesListEvent()
}