package com.example.data

import com.example.example.MatchProfilesModel
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun getProfileMatches(@Query("results") count: Int): Response<MatchProfilesModel>
}