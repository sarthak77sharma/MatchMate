package com.example.domain.usecase

import com.example.data.irepo.IRoomQueryRepo

class UpdateProfileUsecase(private val roomQueryRepo : IRoomQueryRepo) {
    suspend fun execute(profile : String,status : Int){
        roomQueryRepo.updateProfile(profile,status)
    }
}