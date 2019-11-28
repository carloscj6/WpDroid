/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.ui.recyclerview.itemViews

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.models.category.CategoryResponse
import com.revosleap.wpdroid.utils.callbacks.CategorySelection
import org.jetbrains.anko.AnkoLogger
import org.sufficientlysecure.htmltextview.HtmlTextView
import java.util.*

class ItemViewCategory : ItemViewBinder<CategoryResponse, ItemViewCategory.CategoryViewHolder>() {
    private var categorySelection:CategorySelection?=null

    override fun onBindViewHolder(holder: CategoryViewHolder, item: CategoryResponse) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): CategoryViewHolder {
        return CategoryViewHolder(inflater.inflate(R.layout.item_category, parent, false))
    }

    fun setCategorySelection(categoryClick: CategorySelection){
        categorySelection= categoryClick
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {
        private var categoryText:TextView = itemView.findViewById(R.id.textViewCategoryList)
        fun bind(categoryResponse: CategoryResponse) {
            val name = categoryResponse.name
            val category = name?.substring(0,1)?.toUpperCase(Locale.getDefault()) + name?.substring(1)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                categoryText.text = Html.fromHtml(category,Html.FROM_HTML_MODE_COMPACT)
            } else categoryText.text = Html.fromHtml(category)

            itemView.setOnClickListener {
                categorySelection?.onCategorySelected(categoryResponse.id!!,categoryText.text.toString())
            }

        }
    }
}