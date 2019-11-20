package com.revosleap.wpdroid.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.components.RecyclerViewPagination
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewTag
import com.revosleap.wpdroid.ui.recyclerview.models.tags.TagResponse
import com.revosleap.wpdroid.utils.callbacks.TagSelected
import com.revosleap.wpdroid.utils.misc.PreferenceLoader
import com.revosleap.wpdroid.utils.misc.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.tag_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheetTags : BottomSheetDialogFragment(), TagSelected {
    private val itemViewTag = ItemViewTag()
    private val tagAdapter = WpDroidAdapter()
    private lateinit var preferenceLoader:PreferenceLoader
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferenceLoader= PreferenceLoader(context!!)
        itemViewTag.setTagClick(this)
        tagAdapter.register(itemViewTag)
        return inflater.inflate(R.layout.tag_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        instantiateViews()
        getTags(1)
    }

    override fun onTagSelected(tagResponse: TagResponse) {
        BottomSheetItems.getInstance(tagResponse.id!!, Utilities.ITEM_TAG).show(activity?.supportFragmentManager!!,"Tags")
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