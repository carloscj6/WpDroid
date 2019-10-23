package com.revosleap.wpdroid.ui.recyclerview.models.category


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
    @SerializedName("up")
    var up: List<Up?>?,
    @SerializedName("wp:post_type")
    var wpPostType: List<WpPostType?>?
)