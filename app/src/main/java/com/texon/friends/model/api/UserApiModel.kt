package com.texon.friends.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserApiModel {
    private const val URL = "https://randomuser.me/api/"

    //retrofit builder
    fun getApiClient(): UserInterface {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(UserInterface::class.java)
    }

}