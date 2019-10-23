package com.revosleap.wpdroid.ui.recyclerview.models.search


import com.google.gson.annotations.SerializedName

data class Self(
    @SerializedName("embeddable")
    var embeddable: Boolean?,
    @SerializedName("href")
    var href: String?
)