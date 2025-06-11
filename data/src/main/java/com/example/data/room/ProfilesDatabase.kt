package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 3,
    exportSchema = true,
    entities = [ProfilesEntity::class]
)
abstract class ProfilesDatabase : RoomDatabase() {
    abstract val profilesDao : ProfilesDao
}