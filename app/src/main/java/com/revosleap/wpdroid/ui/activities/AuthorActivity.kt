package com.revosleap.wpdroid.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.components.RecyclerViewPagination
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewBlog
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.ui.recyclerview.models.user.UserResponse
import com.revosleap.wpdroid.utils.misc.PreferenceLoader
import com.revosleap.wpdroid.utils.misc.Themer
import com.revosleap.wpdroid.utils.misc.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_author.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorActivity : AppCompatActivity(),AnkoLogger {
    private var wpDataService: GetWpDataService? = null
    private val authorAdapter = WpDroidAdapter()
    lateinit var preferenceLoader:PreferenceLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Themer(this).setTheme()
        setContentView(R.layout.activity_author)
        preferenceLoader= PreferenceLoader(this)
        wpDataService = RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        instantiateRV()
        getAuthor()
    }

    override fun onResume() {
        super.onResume()
        Themer(this).setTheme()
    }

    private fun getAuthor() {
        val authorId = intent.extras?.getLong(Utilities.AUTHOR_ID)
        val call = wpDataService?.getWpUser(authorId!!)
        call?.enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                updateUI(Utilities.ERROR)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val author = response.body()
                    Picasso.with(this@AuthorActivity).load(author?.avatarUrls?.x96)
                        .placeholder(R.drawable.author_placehoder)
                        .into(circleImageViewAuthor)
                    textViewName.setHtml(author?.name!!)
                    textViewIntro.setHtml(author.description!!)
                    updateUI(Utilities.SUCCESS)
                    getAuthorPosts(1)
                } else {
                    updateUI(Utilities.ERROR)
                    buttonRetry.visibility = View.GONE
                }
            }
        })
    }

    fun getAuthorPosts(page: Long) {
        val authorId = intent.extras?.getLong(Utilities.AUTHOR_ID)
        val call = wpDataService?.getAuthorPosts(authorId!!, preferenceLoader.postLimit, page)
        call?.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                warn("failed getting posts \n ${t.message} \n${call.request().url()}")
            }

            override fun onResponse(
                call: Call<List<PostResponse>>,
                response: Response<List<PostResponse>>
            ) {
                if (response.isSuccessful) {
                    progressBarAuthorPosts?.visibility= View.GONE
                    recyclerViewAuthorPosts?.visibility= View.VISIBLE
                    authorAdapter.addItems(response.body()!!)
                }
                warn("Success \n${call.request().url()} \n ${response.body()?.size}")
            }
        })
    }

    private fun instantiateRV() {

        authorAdapter.register(ItemViewBlog())
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewAuthorPosts.apply {
            layoutManager = linearLayoutManager
            adapter = authorAdapter
            addOnScrollListener(object : RecyclerViewPagination(linearLayoutManager) {
                override fun onLoadMore(page: Long, totalItemsCount: Int, view: RecyclerView) {
                    getAuthorPosts(page)
                }

            })
            hasFixedSize()
        }
    }

    private fun updateUI(status: String) {
        when (status) {
            Utilities.ERROR -> {
                progressBarAuthor.visibility = View.GONE
                linearLayoutError.visibility = View.VISIBLE
                linearLayoutAuthor.visibility = View.GONE
            }
            Utilities.LOADING -> {
                progressBarAuthor.visibility = View.VISIBLE
                linearLayoutError.visibility = View.GONE
                linearLayoutAuthor.visibility = View.GONE
            }

            Utilities.SUCCESS -> {
                progressBarAuthor.visibility = View.GONE
                linearLayoutError.visibility = View.GONE
                linearLayoutAuthor.visibility = View.VISIBLE
            }
        }
    }
}
