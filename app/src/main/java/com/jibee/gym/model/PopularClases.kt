package com.jibee.gym.model


import com.google.gson.annotations.SerializedName

data class PopularClases(
    @SerializedName("favorite")
    var favorite: Boolean,
    @SerializedName("location")
    val location: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("time")
    val time: String,
    @SerializedName("title")
    val title: String
)