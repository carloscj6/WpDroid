package com.revosleap.wpdroid.ui.recyclerview.models.search


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("_links")
    var links: Links?,
    @SerializedName("subtype")
    var subtype: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("url")
    var url: String?
)