package com.revosleap.wpdroid.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.components.RecyclerViewPagination
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewBlog
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.utils.misc.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.tag_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheetItems : FullScreenBottomSheet() {
    private val itemAdapter = WpDroidAdapter()
    private var wpDataService: GetWpDataService? = null
    var id: Long? = null
    var itemType: String? = null

    companion object {
        fun getInstance(id: Long, itemType: String): BottomSheetItems {
            val bottomSheetItems = BottomSheetItems()
            bottomSheetItems.itemType = itemType
            bottomSheetItems.id = id
            return bottomSheetItems
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemAdapter.register(ItemViewBlog())
        wpDataService = RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        return inflater.inflate(R.layout.tag_layout, container, false)
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
                } else {
                    updateUI(Utilities.ERROR)
                    buttonRetry.visibility = View.GONE
                }
            }
        })
    }

    fun getAuthorPosts(page: Long) {
        val call = wpDataService?.getAuthorPosts(id!!, 30)
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
                } else {
                    updateUI(Utilities.ERROR)
                    buttonRetry.visibility = View.GONE
                }
            }
        })
    }

    fun getTagPosts(page: Long) {
        val call = wpDataService?.getWptTagPosts(id!!, 30)
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
                } else {
                    updateUI(Utilities.ERROR)
                    buttonRetry.visibility = View.GONE
                }
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