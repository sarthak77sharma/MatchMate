package com.example.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfilesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfiles(profileList: List<ProfilesEntity>)

    @Query("SELECT * FROM match_profiles WHERE gender = :gender")
    fun getAllMatchesGender(gender : String): Flow<List<ProfilesEntity>>

    @Query("UPDATE match_profiles SET status = :status WHERE userId = :uuid")
    fun updateProfile(uuid : String, status: Int)

    @Query("SELECT * FROM match_profiles")
    fun getAllMatches(): Flow<List<ProfilesEntity>>

    @Query("SELECT * FROM match_profiles WHERE status > -1")
    fun getProfileHistory() : Flow<List<ProfilesEntity>>

}