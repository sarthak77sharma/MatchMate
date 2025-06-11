package com.example.matchmate.utils

import com.example.example.Results

sealed class ProfilesStates {

    data object Ideal: ProfilesStates()
    data class Success(val matchProfileList: MutableList<Results>): ProfilesStates()

    data class Error(val message: String): ProfilesStates()
}