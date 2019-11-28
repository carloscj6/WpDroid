package com.revosleap.wpdroid.ui.recyclerview.models.misc

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class SiteCategory(
    @Id var id: Long = 0,
    var categoryTitle: String? = null
)