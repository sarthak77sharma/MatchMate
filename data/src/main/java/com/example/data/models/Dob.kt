package com.example.example

import com.google.gson.annotations.SerializedName


data class Dob(

    @SerializedName("date") var date: String? = null,
    @SerializedName("age") var age: Int = 0

)