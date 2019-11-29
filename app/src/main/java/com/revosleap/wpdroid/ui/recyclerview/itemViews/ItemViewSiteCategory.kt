/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 *  If not, see <https://www.gnu.org/licenses/>.
 */

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
import com.revosleap.wpdroid.utils.misc.ObjectBox
import com.revosleap.wpdroid.utils.misc.Websites

class ItemViewSiteCategory() :
    ItemViewBinder<SiteCategory, ItemViewSiteCategory.ItemViewSiteCatHolder>() {


    inner class ItemViewSiteCatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemText: TextView = itemView.findViewById(R.id.textViewSiteCategory)
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerViewSites)
        private val siteAdapter = WpDroidAdapter()
        fun bind(string: SiteCategory) {
            siteAdapter.register(ItemViewSiteName())

            itemText.text = string.categoryTitle
            val site = Websites.siteCategoryBox.get(string.id)
            siteAdapter.addItems(site.sites)
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