package com.revosleap.wpdroid.ui.recyclerview.models.category


import com.google.gson.annotations.SerializedName

data class Up(
    @SerializedName("embeddable")
    var embeddable: Boolean?,
    @SerializedName("href")
    var href: String?
)