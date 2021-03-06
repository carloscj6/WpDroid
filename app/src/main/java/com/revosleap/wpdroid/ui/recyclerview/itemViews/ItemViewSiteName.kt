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
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.models.misc.Website
import com.revosleap.wpdroid.utils.callbacks.ItemSelected

class ItemViewSiteName : ItemViewBinder<Website, ItemViewSiteName.SiteViewHolder>() {
    private var itemSelected: ItemSelected? = null
    private var wpDroidAdapter: WpDroidAdapter? = null

    class SiteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.textViewSiteName)
        private val siteUrl: TextView = itemView.findViewById(R.id.textViewSiteUrl)
        val delete: ImageButton = itemView.findViewById(R.id.imageButtonDeleteSite)

        fun bind(website: Website) {
            name.text = website.site
            siteUrl.text = website.url

        }

    }

    fun setItemSelectedListener(itemSelected: ItemSelected) {
        this.itemSelected = itemSelected
    }

    override fun onBindViewHolder(holder: SiteViewHolder, item: Website) {
        holder.bind(item)
        wpDroidAdapter = adapter as WpDroidAdapter
        holder.itemView.setOnLongClickListener {
            holder.delete.visibility = View.VISIBLE
            true
        }

        holder.delete.setOnClickListener {
            wpDroidAdapter?.notifyItemRemoved(holder.adapterPosition)
        }
        holder.itemView.setOnClickListener {

            itemSelected?.onSelectItems(item)
        }

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): SiteViewHolder {

        return SiteViewHolder(inflater.inflate(R.layout.item_site, parent, false))

    }


}