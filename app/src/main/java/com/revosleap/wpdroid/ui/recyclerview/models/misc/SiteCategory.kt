package com.revosleap.wpdroid.ui.recyclerview.models.misc

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class SiteCategory(
    @Id var id: Long = 0,
    var categoryTitle: String? = null
){
    @Backlink (to = "siteCategory")
    lateinit var sites:ToMany<Website>
}