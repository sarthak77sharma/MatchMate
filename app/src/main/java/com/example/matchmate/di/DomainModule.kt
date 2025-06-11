package com.example.matchmate.di

import com.example.data.irepo.IFetchProfilesRepo
import com.example.data.irepo.IRoomQueryRepo
import com.example.domain.usecase.UpdateProfileUsecase
import com.example.domain.usecase.FetchProfilesUsecase
import com.example.domain.usecase.GetProfilesFromRoomUsecase
import com.example.domain.usecase.ProfileHistoryUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideFetchProfilesUseCase(fetchProfilesRepo : IFetchProfilesRepo): FetchProfilesUsecase {
        return FetchProfilesUsecase(fetchProfilesRepo = fetchProfilesRepo)
    }

    @Provides
    fun provideUpdateProfileUseCase(roomqueryRepo : IRoomQueryRepo): UpdateProfileUsecase {
        return UpdateProfileUsecase(roomqueryRepo )
    }

    @Provides
    fun provideGetProfilesUseCase(roomqueryRepo : IRoomQueryRepo): GetProfilesFromRoomUsecase {
        return GetProfilesFromRoomUsecase(roomqueryRepo)
    }

    @Provides
    fun provideGetProfileHistoryUseCase(roomqueryRepo : IRoomQueryRepo): ProfileHistoryUsecase {
        return ProfileHistoryUsecase(roomqueryRepo)
    }


}