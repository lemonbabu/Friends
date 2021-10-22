package com.texon.friends.model.data

import com.google.gson.annotations.SerializedName

class UserApiResponse (
    @SerializedName("results")
    var userList: ArrayList<UserData>
    )