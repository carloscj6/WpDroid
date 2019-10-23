package com.revosleap.wpdroid.ui.recyclerview.models.tags


import com.google.gson.annotations.SerializedName

data class TagResponse(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: Int?,
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
    @SerializedName("taxonomy")
    var taxonomy: String?
)