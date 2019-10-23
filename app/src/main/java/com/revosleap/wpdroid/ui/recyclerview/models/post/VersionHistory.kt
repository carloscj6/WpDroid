package com.revosleap.wpdroid.ui.recyclerview.models.post


import com.google.gson.annotations.SerializedName

data class VersionHistory(
    @SerializedName("count")
    var count: Int,
    @SerializedName("href")
    var href: String
)