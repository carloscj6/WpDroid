package com.revosleap.wpdroid.ui.recyclerview.models.comment


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("collection")
    var collection: List<Collection?>?,
    @SerializedName("self")
    var self: List<Self?>?,
    @SerializedName("up")
    var up: List<Up?>?
)