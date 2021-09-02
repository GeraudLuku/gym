package com.jibee.gym.model


import com.google.gson.annotations.SerializedName

data class Gyms(
    @SerializedName("gyms")
    val gyms: List<Gym>
)