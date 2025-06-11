package com.example.example

import com.example.data.room.ProfilesEntity
import com.google.gson.annotations.SerializedName


data class Results(

    @SerializedName("gender") var gender: String = "",
    @SerializedName("name") var name: Name = Name(),
    @SerializedName("location") var location: Location = Location(),
    @SerializedName("email") var email: String = "",
    @SerializedName("login") var login: Login = Login(),
    @SerializedName("dob") var dob: Dob? = Dob(),
    @SerializedName("registered") var registered: Registered? = Registered(),
    @SerializedName("phone") var phone: String = "",
    @SerializedName("cell") var cell: String = "",
    @SerializedName("id") var id: Id? = Id(),
    @SerializedName("picture") var picture: Picture = Picture(),
    @SerializedName("nat") var nat: String? = ""

)
