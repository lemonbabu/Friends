package com.texon.friends.model.api

import com.texon.friends.model.data.UserApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//Interfacing data from Api to data class
interface UserInterface {
    @GET(".")
    fun getUsers(@Query("results") result: Int): Call<UserApiResponse>
}