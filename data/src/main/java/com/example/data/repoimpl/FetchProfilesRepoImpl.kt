package com.example.data.repoimpl

import android.location.Location
import android.util.Log
import com.example.data.ApiService
import com.example.data.irepo.IFetchProfilesRepo
import com.example.data.room.ProfilesDao
import com.example.data.room.ProfilesEntity
import com.example.example.MatchProfilesModel
import com.example.example.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.roundToInt

class FetchProfilesRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val profilesDao: ProfilesDao
) : IFetchProfilesRepo{

    lateinit var matchProfiles : Flow<List<ProfilesEntity>>

    override suspend fun fetchProfiles(
        onSuccess: (MatchProfilesModel) -> Unit,
        onFailure: (String) -> Unit
    ) : Flow<List<ProfilesEntity>> {

        Log.d("sarthak", "fetchProfiles:repoimpl ")
        if(apiService.getProfileMatches(10).isSuccessful){
            apiService.getProfileMatches(10).body()?.let { matchProfileRes ->
                withContext(Dispatchers.IO) {
                    saveProfilesToRoom(matchProfileRes)
                }
                val profileMatches =
                matchProfileRes.results.map { profiles ->

                    val score =  ProfilesEntity.calculateScore(profiles)

                    ProfilesEntity(
                        userId = profiles.login.uuid,
                        name = "${profiles.name.title} ${profiles.name.first} ${profiles.name.last}" ,
                        profilePicUrl = profiles.picture.medium,
                        gender = profiles.gender,
                        city = "${profiles.location.city}, ${profiles.location.country}",
                        profileScore = score,
                        age = profiles.dob!!.age
                    )
                }



                matchProfiles = listOf(profileMatches).asFlow()
            }
        }
        return matchProfiles
       // apiService.getProfileMatches(10).body()?.let { onSuccess(it) }
    }

    private fun saveProfilesToRoom(response : MatchProfilesModel){
        response.let { matchProfileRes ->
            val profileMatches =
                matchProfileRes.results.map { profiles ->

                    val score =  ProfilesEntity.calculateScore(profiles)

                    ProfilesEntity(
                        userId = profiles.login.uuid,
                        name = "${profiles.name.title} ${profiles.name.first} ${profiles.name.last}" ,
                        profilePicUrl = profiles.picture.medium,
                        gender = profiles.gender,
                        city = "${profiles.location.city}, ${profiles.location.state}",
                        profileScore = score,
                        age = profiles.dob!!.age

                    )
                }
            profilesDao.insertProfiles(profileMatches)
        }
    }
}

private fun ProfilesEntity.Companion.calculateScore(profiles: Results): Int {

    val dis = calculateDistanceInMeters(
        profiles.location.coordinates!!.latitude!!.toDouble(),
        profiles.location.coordinates!!.longitude!!.toDouble(),
        28.6139,
        77.2090
    )
    val disScore =  distanceToScore(dis)

    val ageScore = ageDifferenceToScore(profiles.dob!!.age!!, 27)


    Log.d("calculateDistanceInMeters", "calculateScore: ${ageScore}")

    return disScore+ageScore
}

fun calculateDistanceInMeters(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Float {
    val loc1 = Location("").apply {
        latitude = lat1
        longitude = lon1
    }

    val loc2 = Location("").apply {
        latitude = lat2
        longitude = lon2
    }
    val distanceInMeters = loc1.distanceTo(loc2)
    return distanceInMeters / 1000f
}

fun distanceToScore(distance: Float): Int {
    val maxDistance = 20000f
    val minDistance = 1f

    val clamped = distance.coerceIn(minDistance, maxDistance)

    val normalized = 1 - (clamped - minDistance) / (maxDistance - minDistance)
    val score = 1 + normalized * 49

    return score.roundToInt()
}

fun ageDifferenceToScore(age1: Int, age2: Int): Int {
    val maxDiff = 50
    val minDiff = 1
    val diff = abs(age1 - age2).coerceIn(minDiff, maxDiff)

    val normalized = 1 - (diff - minDiff).toFloat() / (maxDiff - minDiff)
    val score = 1 + normalized * 49

    return score.roundToInt()
}