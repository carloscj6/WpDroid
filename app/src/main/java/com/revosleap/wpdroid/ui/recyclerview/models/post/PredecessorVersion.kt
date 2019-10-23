package com.revosleap.wpdroid.ui.recyclerview.models.post


import com.google.gson.annotations.SerializedName

data class PredecessorVersion(
    @SerializedName("href")
    var href: String,
    @SerializedName("id")
    var id: Int
)