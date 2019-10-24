package com.revosleap.wpdroid.ui.recyclerview.models.post


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("about")
    var about: List<About?>?,
    @SerializedName("author")
    var author: List<Author?>?,
    @SerializedName("collection")
    var collection: List<Collection?>?,
    @SerializedName("curies")
    var curies: List<Cury?>?,
    @SerializedName("predecessor-version")
    var predecessorVersion: List<PredecessorVersion?>?,
    @SerializedName("replies")
    var replies: List<Reply?>?,
    @SerializedName("self")
    var self: List<Self?>?,
    @SerializedName("version-history")
    var versionHistory: List<VersionHistory?>?,
    @SerializedName("wp:attachment")
    var wpAttachment: List<WpAttachment?>?,
    @SerializedName("wp:featuredmedia")
    var wpFeaturedmedia: List<WpFeaturedmedia?>?,
    @SerializedName("wp:term")
    var wpTerm: List<WpTerm?>?
)