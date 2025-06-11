package com.example.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match_profiles")
data class ProfilesEntity(
    @PrimaryKey
    val userId: String,
    val name: String,
    val profilePicUrl: String,
    val profileScore : Int = 0,
    val gender : String,
    val age : Int,
    val city: String,
    val status: Int = -1,
) {
    companion object
}

/*@Entity(tableName = "my_profile")
data class MyProfileEntity(
    @PrimaryKey
    val userId: String,
    val name: String,
    val profilePicUri: String,
    val gender : String,
    val city: String,
    val age: Int,
)*/
