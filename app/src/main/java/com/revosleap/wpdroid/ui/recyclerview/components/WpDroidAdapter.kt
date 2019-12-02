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

package com.revosleap.wpdroid.ui.recyclerview.components

import com.drakeet.multitype.MultiTypeAdapter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn


class WpDroidAdapter : MultiTypeAdapter(),AnkoLogger {
    private val adapterItems = items.toMutableList()
    var selectionModeActive= false



    fun addItems(itemList: List<Any>) {
        adapterItems.addAll(itemList)
        items = adapterItems
        notifyDataSetChanged()
    }

    fun addManyItems(itemList: List<Any>){
        items= itemList
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