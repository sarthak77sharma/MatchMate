package com.example.data.repoimpl

import com.example.data.irepo.IRoomQueryRepo
import com.example.data.room.ProfilesDao
import com.example.data.room.ProfilesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomQueryRepoImpl @Inject constructor(
    private val profilesDao: ProfilesDao
) : IRoomQueryRepo{
    override suspend fun updateProfile(profile: String,status : Int) {
        profilesDao.updateProfile(profile,status)
    }

    override suspend fun getMatchProfiles(): Flow<List<ProfilesEntity>> {
        return profilesDao.getAllMatches()
    }

    override suspend fun getProfileHistory(): Flow<List<ProfilesEntity>> {
        return profilesDao.getProfileHistory()
    }
}