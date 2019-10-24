package com.revosleap.wpdroid.ui.recyclerview.models.post


import com.google.gson.annotations.SerializedName

data class Title(
    @SerializedName("rendered")
    var rendered: String?
)