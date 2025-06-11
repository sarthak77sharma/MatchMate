package com.example.example

import com.google.gson.annotations.SerializedName


data class Location(

    @SerializedName("street") var street: Street? = Street(),
    @SerializedName("city") var city: String = "",
    @SerializedName("state") var state: String = "",
    @SerializedName("country") var country: String = "",
    @SerializedName("postcode") var postcode: String = "",
    @SerializedName("coordinates") var coordinates: Coordinates? = Coordinates(),
    @SerializedName("timezone") var timezone: Timezone? = Timezone()

)