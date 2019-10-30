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