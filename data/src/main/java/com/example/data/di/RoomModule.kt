package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.room.ProfilesDao
import com.example.data.room.ProfilesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideProfileDatabase(@ApplicationContext context: Context): ProfilesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ProfilesDatabase::class.java,
            "profiles.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideProfileDao(profileDatabase: ProfilesDatabase): ProfilesDao {
        return profileDatabase.profilesDao
    }
}