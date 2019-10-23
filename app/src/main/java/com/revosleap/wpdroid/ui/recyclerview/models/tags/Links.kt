package com.revosleap.wpdroid.ui.recyclerview.models.tags


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("about")
    var about: List<About?>?,
    @SerializedName("collection")
    var collection: List<Collection?>?,
    @SerializedName("curies")
    var curies: List<Cury?>?,
    @SerializedName("self")
    var self: List<Self?>?,
    @SerializedName("wp:post_type")
    var wpPostType: List<WpPostType?>?
)