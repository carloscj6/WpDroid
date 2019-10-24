package com.revosleap.wpdroid.ui.recyclerview.components

import com.drakeet.multitype.MultiTypeAdapter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn


class WpDroidAdapter : MultiTypeAdapter(),AnkoLogger {
    private val adapterItems = items.toMutableList()

    fun addItems(itemList: List<Any>) {
        adapterItems.addAll(itemList)
        items = adapterItems
        notifyDataSetChanged()
    }

    fun appendItems(itemList: MutableList<Any>) {
        adapterItems.addAll(itemList)
        items = adapterItems
        notifyDataSetChanged()
    }

    fun clearItems() {
        adapterItems.clear()
        notifyDataSetChanged()
    }

    fun addItemToTop(item: Any) {
        adapterItems.add(0, item)
        items = adapterItems
        notifyItemInserted(0)
    }

    fun addItemToBottom(item: Any) {
        adapterItems.add(adapterItems.size + 1, item)
        items = adapterItems
        notifyItemInserted(adapterItems.size + 1)
    }
}