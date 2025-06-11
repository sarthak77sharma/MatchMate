package com.example.data.di

import com.example.data.ApiService
import com.example.data.irepo.IFetchProfilesRepo
import com.example.data.irepo.IRoomQueryRepo
import com.example.data.repoimpl.FetchProfilesRepoImpl
import com.example.data.repoimpl.RoomQueryRepoImpl
import com.example.data.room.ProfilesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    fun provideProfileRepository(apiService: ApiService , profilesDao: ProfilesDao): IFetchProfilesRepo =
        FetchProfilesRepoImpl(apiService,profilesDao)

    @Provides
    fun provideRoomQueryRepo(profilesDao: ProfilesDao) : IRoomQueryRepo =
        RoomQueryRepoImpl(profilesDao)
}


