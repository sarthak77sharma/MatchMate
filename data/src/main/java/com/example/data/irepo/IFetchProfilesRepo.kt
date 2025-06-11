package com.example.data.irepo

import com.example.data.room.ProfilesEntity
import com.example.example.MatchProfilesModel
import com.example.example.Results
import kotlinx.coroutines.flow.Flow

interface IFetchProfilesRepo {

    suspend fun fetchProfiles( onSuccess : (MatchProfilesModel) -> Unit, onFailure : (String)->Unit) : Flow<List<ProfilesEntity>>

}