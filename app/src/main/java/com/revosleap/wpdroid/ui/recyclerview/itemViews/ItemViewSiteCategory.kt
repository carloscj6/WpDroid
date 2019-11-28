package com.revosleap.wpdroid.ui.recyclerview.itemViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.models.misc.SiteCategory
import com.revosleap.wpdroid.ui.recyclerview.models.misc.Website
import com.revosleap.wpdroid.utils.misc.ObjectBox

class ItemViewSiteCategory :
    ItemViewBinder<SiteCategory, ItemViewSiteCategory.ItemViewSiteCatHolder>() {


    inner class ItemViewSiteCatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemText: TextView = itemView.findViewById(R.id.textViewSiteCategory)
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerViewSites)
        private val siteAdapter = WpDroidAdapter()
        fun bind(string: SiteCategory) {
            siteAdapter.register(ItemViewSiteName())

            itemText.text = string.categoryTitle
            val sites = mutableListOf<Website>()

            ObjectBox.websitesBox.all.forEach {
                if (it.category == string) {
                    sites.add(it)
                }
            }

            siteAdapter.addItems(sites)
            recyclerView.apply {
                adapter = siteAdapter
                layoutManager = GridLayoutManager(itemView.context, 2)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemViewSiteCatHolder, item: SiteCategory) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewSiteCatHolder {
        return ItemViewSiteCatHolder(inflater.inflate(R.layout.site_category_item, parent, false))
    }
}