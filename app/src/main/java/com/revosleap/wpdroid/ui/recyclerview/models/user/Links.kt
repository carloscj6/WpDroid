package com.revosleap.wpdroid.ui.recyclerview.models.user


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("collection")
    var collection: List<Collection?>?,
    @SerializedName("self")
    var self: List<Self?>?
)