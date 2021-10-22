package com.texon.friends.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class UserData(
    @SerializedName("gender")
    var gender: String,
    @SerializedName("name")
    var name: Name,
    @SerializedName("location")
    var location: Location,
    @SerializedName("email")
    var email: String,
    @SerializedName("picture")
    var picture: Picture

): Serializable

