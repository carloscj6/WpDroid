/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.ui.recyclerview.itemViews

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.models.tags.TagResponse
import com.revosleap.wpdroid.utils.callbacks.TagSelected

class ItemViewTag : ItemViewBinder<TagResponse, ItemViewTag.TagItemView>() {
    private var tagSelected: TagSelected? = null
    override fun onBindViewHolder(holder: TagItemView, item: TagResponse) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): TagItemView {
        return TagItemView(inflater.inflate(R.layout.item_tag, parent, false))
    }

    fun setTagClick(tagSelect: TagSelected) {
        tagSelected = tagSelect
    }

    inner class TagItemView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tagText: TextView = itemView.findViewById(R.id.textViewHashTag)

        fun bind(tagResponse: TagResponse) {
            tagText.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            val hashTag = "#${tagResponse.name}"
            tagText.text = hashTag
            itemView.setOnClickListener {
                tagSelected?.onTagSelected(tagResponse)
            }
        }
    }
}