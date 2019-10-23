package com.revosleap.wpdroid.ui.recyclerview.models.media


import com.google.gson.annotations.SerializedName

data class ImageMeta(
    @SerializedName("aperture")
    var aperture: String?,
    @SerializedName("camera")
    var camera: String?,
    @SerializedName("caption")
    var caption: String?,
    @SerializedName("copyright")
    var copyright: String?,
    @SerializedName("created_timestamp")
    var createdTimestamp: String?,
    @SerializedName("credit")
    var credit: String?,
    @SerializedName("focal_length")
    var focalLength: String?,
    @SerializedName("iso")
    var iso: String?,
    @SerializedName("keywords")
    var keywords: List<Any?>?,
    @SerializedName("orientation")
    var orientation: String?,
    @SerializedName("shutter_speed")
    var shutterSpeed: String?,
    @SerializedName("title")
    var title: String?
)