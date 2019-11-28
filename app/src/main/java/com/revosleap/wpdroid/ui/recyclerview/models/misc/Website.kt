package com.revosleap.wpdroid.ui.recyclerview.models.misc

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Website(
    @Id var id: Long = 0,
    var site: String,
    var url: String,
    var category: SiteCategory
)