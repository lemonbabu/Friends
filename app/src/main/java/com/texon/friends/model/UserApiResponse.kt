package com.texon.friends.model

import com.google.gson.annotations.SerializedName
import com.texon.friends.model.data.UserData

class UserApiResponse (
    @SerializedName("results")
    var userList: ArrayList<UserData>
    )