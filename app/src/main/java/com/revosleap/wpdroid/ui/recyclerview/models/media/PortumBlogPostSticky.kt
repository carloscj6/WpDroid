package com.revosleap.wpdroid.ui.recyclerview.models.media


import com.google.gson.annotations.SerializedName

data class PortumBlogPostSticky(
    @SerializedName("file")
    var `file`: String?,
    @SerializedName("height")
    var height: Int?,
    @SerializedName("mime_type")
    var mimeType: String?,
    @SerializedName("source_url")
    var sourceUrl: String?,
    @SerializedName("width")
    var width: Int?
)