/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.activities.TagActivity
import com.revosleap.wpdroid.ui.recyclerview.components.RecyclerViewPagination
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewTag
import com.revosleap.wpdroid.ui.recyclerview.models.tags.TagResponse
import com.revosleap.wpdroid.utils.callbacks.TagSelected
import com.revosleap.wpdroid.utils.misc.PreferenceLoader
import com.revosleap.wpdroid.utils.misc.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.tag_list_view.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentTagList:Fragment(),TagSelected {

    private val itemViewTag = ItemViewTag()
    private val tagAdapter = WpDroidAdapter()
    private lateinit var preferenceLoader: PreferenceLoader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferenceLoader= PreferenceLoader(context!!)
        itemViewTag.setTagClick(this)
        tagAdapter.register(itemViewTag)
        return inflater.inflate(R.layout.tag_list_view,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instantiateViews()
        getTags(1)
    }

    private fun instantiateViews() {
        val gridLayoutManager = GridLayoutManager(context, 3)
        recyclerViewItems.apply {
            adapter = tagAdapter
            layoutManager = gridLayoutManager
            hasFixedSize()
            addOnScrollListener(object : RecyclerViewPagination(gridLayoutManager) {
                override fun onLoadMore(
                    page: Long,
                    totalItemsCount: Int,
                    view: RecyclerView
                ) {
                    getTags(page)
                }
            })
        }
    }

    override fun onTagSelected(tagResponse: TagResponse) {
       context?.startActivity<TagActivity>(Utilities.ITEM_ID_BUNDLE to tagResponse.id,
           Utilities.ITEM_TYPE_BUNDLE to Utilities.ITEM_TAG,
           Utilities.ITEM_TITLE_BUNDLE to tagResponse.name)
    }

    private fun getTags(page: Long) {
        val wpDataService =
            RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        val call = wpDataService?.getWpTags(preferenceLoader.tagLimit, page)
        call?.enqueue(object : Callback<List<TagResponse>> {
            override fun onFailure(call: Call<List<TagResponse>>, t: Throwable) {
                if (page == 1L) {
                    updateUi(Utilities.ERROR)
                }

            }

            override fun onResponse(
                call: Call<List<TagResponse>>,
                response: Response<List<TagResponse>>
            ) {
                if (response.isSuccessful) {
                    tagAdapter.addItems(response.body()!!)
                    updateUi(Utilities.SUCCESS)
                } else {
                    if (page == 1L) {
                        updateUi(Utilities.ERROR)
                    }
                }
            }
        })
    }

    private fun updateUi(status: String) {
        when (status) {
            Utilities.LOADING -> {
                progressBarTags?.visibility = View.VISIBLE
                textViewTagError?.visibility = View.GONE
                recyclerViewItems?.visibility = View.GONE
            }
            Utilities.SUCCESS -> {
                textViewTagError?.visibility = View.GONE
                recyclerViewItems?.visibility = View.VISIBLE
                progressBarTags?.visibility = View.GONE
            }
            Utilities.ERROR -> {
                textViewTagError?.visibility = View.VISIBLE
                recyclerViewItems?.visibility = View.GONE
                progressBarTags?.visibility = View.GONE
            }
        }
    }
}