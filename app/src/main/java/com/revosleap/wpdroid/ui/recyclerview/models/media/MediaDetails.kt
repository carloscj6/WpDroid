package com.revosleap.wpdroid.ui.recyclerview.models.media


import com.google.gson.annotations.SerializedName

data class MediaDetails(
    @SerializedName("file")
    var `file`: String?,
    @SerializedName("height")
    var height: Int?,
    @SerializedName("image_meta")
    var imageMeta: ImageMeta?,
    @SerializedName("sizes")
    var sizes: Sizes?,
    @SerializedName("width")
    var width: Int?
)