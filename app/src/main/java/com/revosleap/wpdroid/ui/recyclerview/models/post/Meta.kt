package com.revosleap.wpdroid.ui.recyclerview.models.post


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("jetpack_publicize_message")
    var jetpackPublicizeMessage: String,
    @SerializedName("spay_email")
    var spayEmail: String
)