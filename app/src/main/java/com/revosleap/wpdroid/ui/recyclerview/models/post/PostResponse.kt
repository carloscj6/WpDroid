package com.revosleap.wpdroid.ui.recyclerview.models.post


import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("author")
    var author: Int,
    @SerializedName("categories")
    var categories: List<Int>,
    @SerializedName("comment_status")
    var commentStatus: String,
    @SerializedName("content")
    var content: Content,
    @SerializedName("date")
    var date: String,
    @SerializedName("date_gmt")
    var dateGmt: String,
    @SerializedName("excerpt")
    var excerpt: Excerpt,
    @SerializedName("featured_media")
    var featuredMedia: Int,
    @SerializedName("format")
    var format: String,
    @SerializedName("guid")
    var guid: Guid,
    @SerializedName("id")
    var id: Int,
    @SerializedName("jetpack_featured_media_url")
    var jetpackFeaturedMediaUrl: String,
    @SerializedName("jetpack_publicize_connections")
    var jetpackPublicizeConnections: List<Any>,
    @SerializedName("jetpack_sharing_enabled")
    var jetpackSharingEnabled: Boolean,
    @SerializedName("jetpack_shortlink")
    var jetpackShortlink: String,
    @SerializedName("link")
    var link: String,
    @SerializedName("_links")
    var links: Links,
    @SerializedName("meta")
    var meta: Meta,
    @SerializedName("modified")
    var modified: String,
    @SerializedName("modified_gmt")
    var modifiedGmt: String,
    @SerializedName("ping_status")
    var pingStatus: String,
    @SerializedName("slug")
    var slug: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("sticky")
    var sticky: Boolean,
    @SerializedName("tags")
    var tags: List<Int>,
    @SerializedName("template")
    var template: String,
    @SerializedName("title")
    var title: Title,
    @SerializedName("type")
    var type: String
)