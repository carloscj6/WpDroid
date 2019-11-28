package com.revosleap.wpdroid.ui.recyclerview.itemViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.models.misc.Website

class ItemViewSiteName : ItemViewBinder<Website, ItemViewSiteName.SiteViewHolder>() {

    class SiteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.textViewSiteName)

        fun bind(website: Website) {
            name.text = website.site
        }
    }

    override fun onBindViewHolder(holder: SiteViewHolder, item: Website) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): SiteViewHolder {
        return SiteViewHolder(inflater.inflate(R.layout.item_site, parent, false))
    }
}