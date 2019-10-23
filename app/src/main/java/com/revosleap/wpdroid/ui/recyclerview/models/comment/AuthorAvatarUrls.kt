package com.revosleap.wpdroid.ui.recyclerview.models.comment


import com.google.gson.annotations.SerializedName

data class AuthorAvatarUrls(
    @SerializedName("24")
    var x24: String?,
    @SerializedName("48")
    var x48: String?,
    @SerializedName("96")
    var x96: String?
)