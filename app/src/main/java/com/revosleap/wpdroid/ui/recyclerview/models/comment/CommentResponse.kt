package com.revosleap.wpdroid.ui.recyclerview.models.comment


import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("author")
    var author: Int?,
    @SerializedName("author_avatar_urls")
    var authorAvatarUrls: AuthorAvatarUrls?,
    @SerializedName("author_name")
    var authorName: String?,
    @SerializedName("author_url")
    var authorUrl: String?,
    @SerializedName("content")
    var content: Content?,
    @SerializedName("date")
    var date: String?,
    @SerializedName("date_gmt")
    var dateGmt: String?,
    @SerializedName("id")
    var id: Long?,
    @SerializedName("link")
    var link: String?,
    @SerializedName("_links")
    var links: Links?,
    @SerializedName("meta")
    var meta: List<Any?>?,
    @SerializedName("parent")
    var parent: Long?,
    @SerializedName("post")
    var post: Long?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("type")
    var type: String?
)