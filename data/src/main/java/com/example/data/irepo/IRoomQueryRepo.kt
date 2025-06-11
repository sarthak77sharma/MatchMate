package com.example.data.irepo

import com.example.data.room.ProfilesEntity
import kotlinx.coroutines.flow.Flow

interface IRoomQueryRepo {
    suspend fun updateProfile(profile: String,status : Int)

    suspend fun getMatchProfiles() : Flow<List<ProfilesEntity>>

    suspend fun getProfileHistory() : Flow<List<ProfilesEntity>>
}