package com.jibee.gym.model


import com.google.gson.annotations.SerializedName

data class Gym(
    @SerializedName("date")
    val date: String,
    @SerializedName("favorite")
    var favorite: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("popular_clasess")
    val popularClasess: List<PopularClases>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("title")
    val title: String
)