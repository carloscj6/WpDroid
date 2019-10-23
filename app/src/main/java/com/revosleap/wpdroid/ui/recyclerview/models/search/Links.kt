package com.revosleap.wpdroid.ui.recyclerview.models.search


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("about")
    var about: List<About?>?,
    @SerializedName("collection")
    var collection: List<Collection?>?,
    @SerializedName("self")
    var self: List<Self?>?
)