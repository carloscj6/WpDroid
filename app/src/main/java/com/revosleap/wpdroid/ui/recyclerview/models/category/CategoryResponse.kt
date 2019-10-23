package com.revosleap.wpdroid.ui.recyclerview.models.category


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
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
    @SerializedName("parent")
    var parent: Int?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("taxonomy")
    var taxonomy: String?
)