package com.example.domain.usecase

import com.example.data.irepo.IRoomQueryRepo
import com.example.data.room.ProfilesEntity
import com.example.example.Results
import kotlinx.coroutines.flow.Flow

class GetProfilesFromRoomUsecase(private val roomQueryRepo : IRoomQueryRepo) {
    suspend fun execute() : Flow<List<ProfilesEntity>> {
        return roomQueryRepo.getMatchProfiles()
    }
}