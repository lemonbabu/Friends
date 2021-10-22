package com.texon.friends.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Name(
    @SerializedName("title")
    var title: String,
    @SerializedName("first")
    var first: String,
    @SerializedName("last")
    var last: String
    ): Serializable
