package com.revosleap.wpdroid.utils.misc

import com.revosleap.wpdroid.ui.recyclerview.models.misc.SiteCategory
import com.revosleap.wpdroid.ui.recyclerview.models.misc.Website


object Websites {
    private val tech = SiteCategory(0, "Technology")
    private val diy = SiteCategory(0, "DIY")
    private val recipes = SiteCategory(0, "Recipes")

    private val revosleap = Website(0, "Revosleap", "revosleap.com", tech)
    private val diyNatural = Website(0, "DIY Natural", "www.diynatural.com", diy)
    private val androidHive = Website(0, "AndroidHive", "www.androidhive.info", tech)
    private val tecMint = Website(0, "TecMint", "tecmint.com", tech)
    private val africanBites = Website(0, "African Bites", "africanbites.com", recipes)

    fun getSites(): MutableList<Website> {
        val sites = mutableListOf<Website>()
        sites.add(revosleap)
        sites.add(diyNatural)
        sites.add(androidHive)
        sites.add(tecMint)
        sites.add(africanBites)
        return sites
    }

    fun getSiteCategory(): MutableList<SiteCategory> {
        val categories = mutableListOf<SiteCategory>()
        categories.add(tech)
        categories.add(diy)
        categories.add(recipes)
        return categories
    }

    fun addDefaultSites() {
        val sites = ObjectBox.websitesBox.all
        if (sites.size == 0) {
            ObjectBox.websitesBox.put(getSites())
        }
    }

    fun addDefaultCategories() {
        val categories = ObjectBox.siteCategoryBox.all
        if (categories.size == 0) {
            ObjectBox.siteCategoryBox.put(getSiteCategory())
        }
    }
}
