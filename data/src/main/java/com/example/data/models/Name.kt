package com.example.example

import com.google.gson.annotations.SerializedName


data class Name(

    @SerializedName("title") var title: String = "",
    @SerializedName("first") var first: String = "",
    @SerializedName("last") var last: String = ""

)