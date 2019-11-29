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

package com.revosleap.wpdroid.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.activities.TagActivity
import com.revosleap.wpdroid.ui.recyclerview.components.RecyclerViewPagination
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewBlog
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.ui.recyclerview.models.post.Title
import com.revosleap.wpdroid.utils.misc.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.tag_list_view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentItems : Fragment(),AnkoLogger {
    private val itemAdapter = WpDroidAdapter()
    private var wpDataService: GetWpDataService? = null
    private var id: Long? = null
    private var itemType: String? = null
    private var title:String?=null
    private var tagActivity:TagActivity?=null

    companion object {
        fun getInstance(id: Long, itemType: String,title: String): FragmentItems {
            val bottomSheetItems = FragmentItems()
            bottomSheetItems.itemType = itemType
            bottomSheetItems.id = id
            bottomSheetItems.title= title
            return bottomSheetItems
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        tagActivity= activity as TagActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemAdapter.register(ItemViewBlog())
        wpDataService = RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        return inflater.inflate(R.layout.tag_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instantiateViews()
        when (itemType) {
            Utilities.ITEM_TAG -> {
                getTagPosts(1)
            }
            Utilities.ITEM_AUTHOR -> {
                getAuthorPosts(1)
            }

            Utilities.ITEM_CATEGORY -> {
                getCategoryPosts(1)
            }
        }
    }

    private fun instantiateViews() {
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerViewItems.apply {
            adapter = itemAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(object : RecyclerViewPagination(linearLayoutManager) {
                override fun onLoadMore(page: Long, totalItemsCount: Int, view: RecyclerView) {
                    when (itemType) {
                        Utilities.ITEM_CATEGORY -> {
                            getCategoryPosts(page)
                        }

                        Utilities.ITEM_AUTHOR -> {
                            getAuthorPosts(page)
                        }

                        Utilities.ITEM_TAG -> {
                            getTagPosts(page)
                        }
                    }

                }

            })
        }
    }

    fun getCategoryPosts(page: Long) {
        tagActivity?.supportActionBar?.title="CATEGORY: $title"
        val call = wpDataService?.getWpPostsCategorized(id!!, 30, page)
        call?.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                updateUI(Utilities.ERROR)
            }

            override fun onResponse(
                call: Call<List<PostResponse>>,
                response: Response<List<PostResponse>>
            ) {
                if (response.isSuccessful) {
                    updateUI(Utilities.SUCCESS)
                    if (response.body() != null && response.body()?.size!! > 0) {
                        itemAdapter.addItems(response.body()!!)
                    }
                }

            }
        })
    }

    fun getAuthorPosts(page: Long) {
        tagActivity?.supportActionBar?.title="AUTHOR: $title"
        val call = wpDataService?.getAuthorPosts(id!!, 30,page)
        call?.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                updateUI(Utilities.ERROR)
            }

            override fun onResponse(
                call: Call<List<PostResponse>>,
                response: Response<List<PostResponse>>
            ) {
                if (response.isSuccessful) {
                    updateUI(Utilities.SUCCESS)
                    if (response.body() != null && response.body()?.size!! > 0) {
                        itemAdapter.addItems(response.body()!!)
                    }
                }
            }
        })
    }

    fun getTagPosts(page: Long) {
        tagActivity?.supportActionBar?.title="TAG: $title"
        val call = wpDataService?.getWptTagPosts(id!!, 30,page)
        call?.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                updateUI(Utilities.ERROR)
            }

            override fun onResponse(
                call: Call<List<PostResponse>>,
                response: Response<List<PostResponse>>
            ) {
                warn("${call.request().url()}")
                if (response.isSuccessful) {
                    updateUI(Utilities.SUCCESS)
                    if (response.body() != null && response.body()?.size!! > 0) {
                        itemAdapter.addItems(response.body()!!)
                    }
                }
//                else {
//                    updateUI(Utilities.ERROR)
//                    buttonRetry.visibility = View.GONE
//                }
            }
        })
    }

    private fun updateUI(state: String) {
        when (state) {
            Utilities.LOADING -> {
                recyclerViewItems?.visibility = View.GONE
                progressBarTags?.visibility = View.VISIBLE
                linearLayoutError?.visibility = View.GONE
            }

            Utilities.ERROR -> {
                recyclerViewItems?.visibility = View.GONE
                progressBarTags?.visibility = View.GONE
                linearLayoutError?.visibility = View.VISIBLE
            }

            Utilities.SUCCESS -> {
                recyclerViewItems?.visibility = View.VISIBLE
                progressBarTags?.visibility = View.GONE
                linearLayoutError?.visibility = View.GONE
            }
        }
    }
}