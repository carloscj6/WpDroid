package com.revosleap.wpdroid.ui.recyclerview.models.user


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("avatar_urls")
    var avatarUrls: AvatarUrls?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: Long?,
    @SerializedName("link")
    var link: String?,
    @SerializedName("_links")
    var links: Links?,
    @SerializedName("meta")
    var meta: List<Any?>?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("url")
    var url: String?
)