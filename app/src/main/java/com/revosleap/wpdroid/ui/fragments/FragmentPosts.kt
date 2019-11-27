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
import com.revosleap.wpdroid.ui.activities.MainActivity
import com.revosleap.wpdroid.ui.activities.SettingsActivity
import com.revosleap.wpdroid.ui.recyclerview.components.RecyclerViewPagination
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewBlog
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.utils.misc.PreferenceLoader
import com.revosleap.wpdroid.utils.misc.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.fragment_main_posts.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.warn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentPosts : Fragment(), AnkoLogger {
    private var mainActivity: MainActivity? = null
    private var item: String? = null
    private var title: String? = null
    private var selectCategoryId: Long? = null
    private val wpDroidAdapter = WpDroidAdapter()
    var wpDataService: GetWpDataService? = null
    lateinit var preferenceLoader: PreferenceLoader
    private var currentPostPage: Long = 1L


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
        preferenceLoader = PreferenceLoader(context)
        wpDataService =
            RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wpDroidAdapter.register(ItemViewBlog())
        return inflater.inflate(R.layout.fragment_main_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instantiateRecyclerView()
        if (selectCategoryId != null) {
            getPosts(1, selectCategoryId)
            mainActivity?.supportActionBar?.title = "Category: $title"
        } else {
            getPosts(1, null)
            mainActivity?.supportActionBar?.title = "Posts"
        }

    }

    private fun instantiateRecyclerView() {

        val linearLayoutManager = LinearLayoutManager(mainActivity)
        recyclerViewPosts.apply {
            adapter = wpDroidAdapter
            layoutManager = linearLayoutManager
            hasFixedSize()
            addOnScrollListener(object : RecyclerViewPagination(linearLayoutManager) {
                override fun onLoadMore(
                    page: Long,
                    totalItemsCount: Int,
                    view: RecyclerView
                ) {
                    if (selectCategoryId != null) {
                        getPosts(page, selectCategoryId)
                    } else getPosts(page, null)
                }
            })
        }

    }

    private fun getPosts(page: Long, categoryId: Long?) {
        currentPostPage = page
        if (page == 1L) {
            updateUi(Utilities.LOADING)
        }
        val call: Call<List<PostResponse>>? = if (categoryId == null) {
            selectCategoryId = null
            wpDataService?.getWpPosts(preferenceLoader.postLimit, page)
        } else {
            selectCategoryId = categoryId
            wpDataService?.getWpPostsCategorized(categoryId, preferenceLoader.postLimit, page)
        }

        call?.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                warn("${call.request().url()} \n ${t.message}")

                when {
                    t.message == "timeout" -> {
                        val sb = frame_layout_posts?.indefiniteSnackbar("Network Timeout")
                        sb?.show()
                        sb?.setAction("Reload") {
                            //reload()
                            sb.dismiss()
                        }
                    }
                    t.message!!.contains("No address associated with hostname", true) -> {
                        progressLoading.visibility = View.GONE
                        val sb = frame_layout_posts?.indefiniteSnackbar("Site not Found")
                        sb?.show()
                        sb?.setAction("Change Site") {
                            mainActivity?.startActivity<SettingsActivity>()
                        }
                    }
                    t.message!!.contains("not verified:", true) -> {
                        progressLoading.visibility = View.GONE
                        mainActivity?.toast("Address may have www or not, please check")
                        val sb = frame_layout_posts.indefiniteSnackbar("Change your address")
                        sb.show()
                        sb.setAction("Change") {
                            mainActivity?.startActivity<SettingsActivity>()
                        }
                    }
                    else -> updateUi(Utilities.ERROR)
                }
            }

            override fun onResponse(
                call: Call<List<PostResponse>>,
                response: Response<List<PostResponse>>
            ) {
                warn(call.request().url().toString())
                if (response.isSuccessful) {
                    if (response.body()?.size != null && response.body()?.size!! > 0) {
                        linearLayoutOops?.visibility = View.GONE
                        recyclerViewPosts?.visibility = View.VISIBLE
                        wpDroidAdapter.addItems(response.body()!!)
                        updateUi(Utilities.SUCCESS)

                    } else {
                        if (page == 1L) {
                            updateUi(Utilities.ERROR)
                            textViewOops.text = "Request is Successful but no post was found!!"
                        }

                    }
                } else {
                    if (page == 1L) {
                        textViewOops.text =
                            "Request is Successful but there seems to be problem with getting posts"
                        updateUi(Utilities.ERROR)
                        buttonRetry.visibility = View.GONE
                    }

                }


            }
        })
    }

    private fun updateUi(status: String) {
        when (status) {
            Utilities.LOADING -> {
                progressLoading?.visibility = View.VISIBLE
                linearLayoutOops?.visibility = View.GONE
                recyclerViewPosts?.visibility = View.GONE
            }
            Utilities.SUCCESS -> {
                linearLayoutOops?.visibility = View.GONE
                recyclerViewPosts?.visibility = View.VISIBLE
                progressLoading?.visibility = View.GONE
            }
            Utilities.ERROR -> {
                linearLayoutOops?.visibility = View.VISIBLE
                recyclerViewPosts?.visibility = View.GONE
                progressLoading?.visibility = View.GONE
            }
        }
        buttonRetry?.setOnClickListener {
            // getCategories(1)
            getPosts(1, null)
        }
    }

    companion object {
        fun getInstance(id: Long?, title: String?): FragmentPosts {
            val fragmentPosts = FragmentPosts()
            fragmentPosts.title = title
            fragmentPosts.selectCategoryId = id
            return fragmentPosts
        }
    }
}