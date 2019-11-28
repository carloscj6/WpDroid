package com.revosleap.wpdroid.utils.misc

import com.revosleap.wpdroid.ui.recyclerview.models.misc.SiteCategory
import com.revosleap.wpdroid.ui.recyclerview.models.misc.Website
import io.objectbox.Box


object Websites {
    private val tech = SiteCategory(0, "Technology")
    private val diy = SiteCategory(0, "DIY")
    private val recipes = SiteCategory(0, "Recipes")
    val uncategorized = SiteCategory(0, "Uncategorized")

    private val revosleap = Website(0, "Revosleap", "revosleap.com")
    private val diyNatural = Website(0, "DIY Natural", "www.diynatural.com")
    private val androidHive = Website(0, "AndroidHive", "www.androidhive.info")
    private val tecMint = Website(0, "TecMint", "tecmint.com")
    private val africanBites = Website(0, "African Bites", "africanbites.com")

    fun getSites(): MutableList<Website> {
        val sites = mutableListOf<Website>()
        revosleap.siteCategory.target= tech
        diyNatural.siteCategory.target= diy
        androidHive.siteCategory.target= tech
        tecMint.siteCategory.target= tech
        africanBites.siteCategory.target= recipes

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
        categories.add(uncategorized)
        return categories
    }

    fun addDefaultSites() {
        val sites = websitesBox.all
        if (sites.size == 0) {
            websitesBox.put(getSites())
        }
    }

    fun addDefaultCategories() {
        val categories = siteCategoryBox.all
        if (categories.size == 0) {
           siteCategoryBox.put(getSiteCategory())
        }
    }
    val websitesBox: Box<Website> = ObjectBox.boxStore.boxFor(Website::class.java)
    val siteCategoryBox: Box<SiteCategory> = ObjectBox.boxStore.boxFor(SiteCategory::class.java)
}
