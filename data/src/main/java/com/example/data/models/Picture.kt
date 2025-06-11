package com.example.example

import com.google.gson.annotations.SerializedName


data class Picture(

    @SerializedName("large") var large: String = "",
    @SerializedName("medium") var medium: String = "",
    @SerializedName("thumbnail") var thumbnail: String = ""

)