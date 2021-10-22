package com.texon.friends.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// user data getting from api Json result
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
    var picture: Picture,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("cell")
    var cell: String

): Serializable


// Name class for access name data
data class Name(
    @SerializedName("title")
    var title: String,
    @SerializedName("first")
    var first: String,
    @SerializedName("last")
    var last: String
): Serializable


// Picture class for access image url data
data class Picture(
    @SerializedName("large")
    var large: String
): Serializable


// Location class for access full location data
data class Location(
    @SerializedName("city")
    var city: String,
    @SerializedName("state")
    var state: String,
    @SerializedName("coordinates")
    var coordinates: Coordinates
): Serializable

// Coordinates class for access full location data
data class Coordinates(
    @SerializedName("latitude")
    var latitude: String,
    @SerializedName("longitude")
    var longitude: String
): Serializable
