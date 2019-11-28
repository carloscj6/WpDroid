package com.revosleap.wpdroid.utils.misc

import android.content.Context
import com.revosleap.wpdroid.ui.recyclerview.models.misc.MyObjectBox
import com.revosleap.wpdroid.ui.recyclerview.models.misc.SiteCategory
import com.revosleap.wpdroid.ui.recyclerview.models.misc.Website
import io.objectbox.Box
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    val websitesBox: Box<Website> = boxStore.boxFor(Website::class.java)
    val siteCategoryBox: Box<SiteCategory> = boxStore.boxFor(SiteCategory::class.java)
}