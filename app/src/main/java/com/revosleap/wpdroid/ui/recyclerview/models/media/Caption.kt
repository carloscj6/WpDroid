package com.revosleap.wpdroid.ui.recyclerview.models.media


import com.google.gson.annotations.SerializedName

data class Caption(
    @SerializedName("rendered")
    var rendered: String?
)