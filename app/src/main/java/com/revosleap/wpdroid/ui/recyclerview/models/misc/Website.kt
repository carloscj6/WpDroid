package com.revosleap.wpdroid.ui.recyclerview.models.misc

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class Website(
    @Id var id: Long = 0,
    var site: String,
    var url: String
){
    lateinit var siteCategory:ToOne<SiteCategory>
    constructor():this(0,"","")
}