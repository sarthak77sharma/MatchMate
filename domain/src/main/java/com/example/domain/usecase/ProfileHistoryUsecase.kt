package com.example.domain.usecase

import com.example.data.irepo.IRoomQueryRepo
import com.example.data.room.ProfilesEntity
import kotlinx.coroutines.flow.Flow

class ProfileHistoryUsecase(private val roomQueryRepo : IRoomQueryRepo) {

    suspend fun execute() : Flow<List<ProfilesEntity>> {
        return roomQueryRepo.getProfileHistory()
    }
}