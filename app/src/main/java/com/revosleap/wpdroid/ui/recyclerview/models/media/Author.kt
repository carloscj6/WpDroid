package com.revosleap.wpdroid.ui.recyclerview.models.media


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("embeddable")
    var embeddable: Boolean?,
    @SerializedName("href")
    var href: String?
)