package com.sms.diaganal

import com.google.gson.annotations.SerializedName

data class Content(
    val name: String,
    @SerializedName("poster-image")
    val poster_image: String
)