package com.revosleap.wpdroid.ui.recyclerview.itemViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.models.misc.AdModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn

class ItemViewAd:ItemViewBinder<AdModel,ItemViewAd.AdViewHolder>(),AnkoLogger {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): AdViewHolder {
        return AdViewHolder(inflater.inflate(R.layout.ad_item,parent,false))
    }
    override fun onBindViewHolder(holder: AdViewHolder, item: AdModel) {
       warn(item.adTitle)
    }

    class AdViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){}
}