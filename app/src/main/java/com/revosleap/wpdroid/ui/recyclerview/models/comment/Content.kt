package com.revosleap.wpdroid.ui.recyclerview.models.comment


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("rendered")
    var rendered: String?
)