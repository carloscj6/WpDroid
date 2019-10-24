package com.revosleap.wpdroid.ui.recyclerview.models.post


import com.google.gson.annotations.SerializedName

data class Cury(
    @SerializedName("href")
    var href: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("templated")
    var templated: Boolean?
)