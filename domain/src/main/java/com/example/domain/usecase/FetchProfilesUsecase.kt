package com.example.domain.usecase

import com.example.data.irepo.IFetchProfilesRepo
import com.example.data.room.ProfilesEntity
import com.example.example.MatchProfilesModel
import com.example.example.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FetchProfilesUsecase(private val fetchProfilesRepo : IFetchProfilesRepo) {
    suspend fun execute(onSuccess : (MatchProfilesModel) -> Unit, onFailure : (String)->Unit) : Flow<List<ProfilesEntity>> {
           return fetchProfilesRepo.fetchProfiles(onSuccess,onFailure)
    }
}