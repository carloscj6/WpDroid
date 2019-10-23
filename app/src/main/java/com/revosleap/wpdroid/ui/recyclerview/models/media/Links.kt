package com.revosleap.wpdroid.ui.recyclerview.models.media


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("about")
    var about: List<About?>?,
    @SerializedName("author")
    var author: List<Author?>?,
    @SerializedName("collection")
    var collection: List<Collection?>?,
    @SerializedName("replies")
    var replies: List<Reply?>?,
    @SerializedName("self")
    var self: List<Self?>?
)