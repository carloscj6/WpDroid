package com.revosleap.wpdroid.ui.recyclerview.models.media


import com.google.gson.annotations.SerializedName

data class MediaResponse(
    @SerializedName("alt_text")
    var altText: String?,
    @SerializedName("author")
    var author: Int?,
    @SerializedName("caption")
    var caption: Caption?,
    @SerializedName("comment_status")
    var commentStatus: String?,
    @SerializedName("date")
    var date: String?,
    @SerializedName("date_gmt")
    var dateGmt: String?,
    @SerializedName("description")
    var description: Description?,
    @SerializedName("guid")
    var guid: Guid?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("jetpack_sharing_enabled")
    var jetpackSharingEnabled: Boolean?,
    @SerializedName("jetpack_shortlink")
    var jetpackShortlink: String?,
    @SerializedName("link")
    var link: String?,
    @SerializedName("_links")
    var links: Links?,
    @SerializedName("media_details")
    var mediaDetails: MediaDetails?,
    @SerializedName("media_type")
    var mediaType: String?,
    @SerializedName("meta")
    var meta: Meta?,
    @SerializedName("mime_type")
    var mimeType: String?,
    @SerializedName("modified")
    var modified: String?,
    @SerializedName("modified_gmt")
    var modifiedGmt: String?,
    @SerializedName("ping_status")
    var pingStatus: String?,
    @SerializedName("post")
    var post: Int?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("source_url")
    var sourceUrl: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("template")
    var template: String?,
    @SerializedName("title")
    var title: Title?,
    @SerializedName("type")
    var type: String?
)