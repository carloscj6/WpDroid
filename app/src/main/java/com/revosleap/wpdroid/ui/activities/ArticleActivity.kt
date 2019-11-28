/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.components.RecyclerViewPagination
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.itemViews.ItemViewComment
import com.revosleap.wpdroid.ui.recyclerview.models.category.CategoryResponse
import com.revosleap.wpdroid.ui.recyclerview.models.comment.CommentResponse
import com.revosleap.wpdroid.ui.recyclerview.models.media.MediaResponse
import com.revosleap.wpdroid.ui.recyclerview.models.misc.ParentComment
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.ui.recyclerview.models.tags.TagResponse
import com.revosleap.wpdroid.ui.recyclerview.models.user.UserResponse
import com.revosleap.wpdroid.utils.misc.PreferenceLoader
import com.revosleap.wpdroid.utils.misc.Themer
import com.revosleap.wpdroid.utils.misc.UtilFun
import com.revosleap.wpdroid.utils.misc.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.content_article.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.warn
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class ArticleActivity : AppCompatActivity(), AnkoLogger,
    SharedPreferences.OnSharedPreferenceChangeListener {
    private var wpDataService: GetWpDataService? = null
    private val commentAdapter = WpDroidAdapter()
    private var postId = 0L
    private lateinit var preferenceLoader: PreferenceLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer(this).setTheme()
        setContentView(R.layout.activity_article)
        setSupportActionBar(toolbar)
        commentAdapter.register(ItemViewComment())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        preferenceLoader = PreferenceLoader(this)

        wpDataService =
            RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        getPost()
        val image = BitmapFactory.decodeResource(resources, R.drawable.blog_item_placeholder)
        warn(preferenceLoader.blurRadius)
        imageViewHeader.setImageBitmap(UtilFun.blurred(this, image, preferenceLoader.blurRadius))
        buttonRetry.setOnClickListener {
            getPost()
        }
        setViewPreferences()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when (item.itemId) {
            R.id.action_settings -> startActivity<SettingsActivity>()

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setViewPreferences() {
        val textSize = UtilFun.getTextSize()
        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize + 10)
        textViewPost.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        textViewPostDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize + 6)
        textViewAuthor.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize + 6)
        textViewComments.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize + 4)
        textViewTags.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize + 4)
        textViewCommentError.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize - 2)
        textViewPost.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            preferenceLoader.lineSpacing.toFloat(),resources.displayMetrics),1f)
    }

    private fun getPost() {
        postId = intent.getLongExtra(Utilities.BLOG_ID, 0)
        val call = wpDataService?.getWpPost(postId)

        call?.enqueue(object : Callback<PostResponse> {
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val post = response.body()
                textViewPost.setHtml(post?.content?.rendered!!, HtmlHttpImageGetter(textViewPost))
                blogTitle.setHtml(post.title!!.rendered!!)
                textViewTitle.setHtml(post.title!!.rendered!!)
                getMedia(post.featuredMedia!!)
                post.categories?.forEach {
                    getCategory(it!!)
                }
                post.tags?.forEach {
                    getTag(it!!)
                }
                val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val sdfInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val date = sdfInput.parse(post.dateGmt!!)
                textViewPostDate.text = sdf.format(date)
                getAuthor(post.author!!)
                getPostComments(1)
                sharePost(response.body()!!)
            }

        })
    }

    private fun sharePost(postResponse: PostResponse){
        val shareIntent= Intent()
        shareIntent.apply {
            action= Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,postResponse.link)
            putExtra(Intent.EXTRA_SUBJECT,textViewTitle.text)
            type = "text/plain"
        }
        fab.setOnClickListener { _ ->
            startActivity(Intent.createChooser(shareIntent,"Share Post Via:"))
        }
    }

    private fun getMedia(id: Long) {

        val call = wpDataService?.getWpMedia(id)
        call?.enqueue(object : Callback<MediaResponse> {
            override fun onFailure(call: Call<MediaResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<MediaResponse>,
                response: Response<MediaResponse>
            ) {
                val target = object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                    }



                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        imageViewHeader.setImageBitmap(
                            UtilFun.blurred(
                                this@ArticleActivity,
                                bitmap!!,
                                preferenceLoader.blurRadius
                            )
                        )
                    }
                }
                Picasso.get().load(response.body()?.sourceUrl).into(target)


            }
        })

    }

    private fun getCategory(id: Long) {
        val call = wpDataService?.getWpCategory(id)
        call?.enqueue(object : Callback<CategoryResponse> {
            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                setCategoryView(response.body())
            }
        })
    }

    private fun getTag(id: Long) {
        val call = wpDataService?.getWpTag(id)
        call?.enqueue(object : Callback<TagResponse> {
            override fun onFailure(call: Call<TagResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<TagResponse>,
                response: Response<TagResponse>
            ) {
                warn(call.request().url().toString())
                setTagView(response.body())
            }
        })
    }

    private fun getAuthor(id: Long) {
        val call = wpDataService?.getWpUser(id)
        call?.enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                updateUI(Utilities.ERROR)
            }

            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    textViewAuthor.setHtml(response.body()?.name!!)
                    textViewAuthor?.setOnClickListener {
                        startActivity<AuthorActivity>(Utilities.AUTHOR_ID to response.body()?.id)
                    }
                    updateUI(Utilities.SUCCESS)
                } else {
                    updateUI(Utilities.ERROR)
                    buttonRetry.text = "Quit"
                    buttonRetry.setOnClickListener {
                        finish()
                    }
                }

            }
        })
    }

    private fun getPostComments(page: Long) {
        val call = wpDataService?.getWpPostComments(postId, preferenceLoader.commentLimit, page)
        call?.enqueue(object : Callback<List<CommentResponse>> {
            override fun onFailure(call: Call<List<CommentResponse>>, t: Throwable) {
                warn(t.message)
                progressBarComments.visibility = View.GONE
                textViewCommentError.visibility = View.VISIBLE
            }

            override fun onResponse(
                call: Call<List<CommentResponse>>,
                response: Response<List<CommentResponse>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.size != null && response.body()?.size!! > 0) {
                        getComments(response.body())
                    } else if (page == 1L) {
                        val error = "This post has no comments"
                        progressBarComments.visibility = View.GONE
                        textViewCommentError.visibility = View.VISIBLE
                        textViewCommentError.text = error
                    }
                }
            }
        })
    }

    fun showComments(comments: List<CommentResponse>) {
        progressBarComments.visibility = View.GONE
        textViewCommentError.visibility = View.GONE
        recyclerViewComments.visibility = View.VISIBLE

        recyclerViewComments.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(this@ArticleActivity)

        }

        commentAdapter.addManyItems(getMainComments(comments))
    }

    private fun getComments(comments: List<CommentResponse>?) {

        val parentComments = mutableListOf<ParentComment>()
        comments?.forEach {
            var parentComment: ParentComment? = null
            if (it.parent == 0L) {
                val childComments = getChildComments(comments, it.id!!)
                parentComment = ParentComment(childComments, it)
            }

            if (parentComment != null) {
                parentComments.add(parentComment)
            }
        }
        progressBarComments.visibility = View.GONE
        textViewCommentError.visibility = View.GONE
        recyclerViewComments.visibility = View.VISIBLE
        val manager = LinearLayoutManager(this)
        recyclerViewComments.apply {

            adapter = commentAdapter
            layoutManager = manager
            addOnScrollListener(object : RecyclerViewPagination(manager) {
                override fun onLoadMore(page: Long, totalItemsCount: Int, view: RecyclerView) {
                    getPostComments(page)
                }

            })

        }
        commentAdapter.addManyItems(parentComments)
    }

    private fun getChildComments(
        comments: List<CommentResponse>?,
        id: Long
    ): MutableList<CommentResponse> {
        val childComments = mutableListOf<CommentResponse>()
        comments?.forEach {
            if (it.parent == id) {
                childComments.add(it)
            }
        }
        return childComments
    }

    private fun getMainComments(comments: List<CommentResponse>?): List<ParentComment> {
        val mainComments = mutableListOf<ParentComment>()
        comments?.forEach {
            val item = getMainComment(mainComments, it.id!!)
            if (item != null) {
                item.comments?.add(it)
            }
        }
        return mainComments
    }

    private fun getMainComment(
        parentComments: MutableList<ParentComment>,
        id: Long
    ): ParentComment? {
        parentComments.forEach {
            if (parentComments.isNotEmpty() && it.comments != null && it.comments!![0].id == id) {
                return it
            }
        }
        return null
    }

    fun setTagView(tagResponse: TagResponse?) {
        val view = findViewById<View>(android.R.id.content) as ViewGroup
        val categoryView = layoutInflater.inflate(R.layout.item_tag, view, false)
        val textView = categoryView.findViewById<TextView>(R.id.textViewHashTag)
        val frameLayoutTAG = categoryView.findViewById<FrameLayout>(R.id.frameLayoutTag)
        frameLayoutTAG.layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT
        val hashTag = "#${tagResponse?.name}"
        textView.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, UtilFun.getTextSize())
        textView.text = hashTag
        categoryView.setOnClickListener {
            startActivity<TagActivity>(Utilities.ITEM_ID_BUNDLE to tagResponse!!.id,
                    Utilities.ITEM_TYPE_BUNDLE to Utilities.ITEM_TAG,
                    Utilities.ITEM_TITLE_BUNDLE to tagResponse.name)
        }
        flowLayoutTags.addView(categoryView)
    }

    private fun setCategoryView(categoryResponse: CategoryResponse?) {
        val view = findViewById<View>(android.R.id.content) as ViewGroup
        val categoryView = layoutInflater.inflate(R.layout.category_item, view, false)
        val textView = categoryView.findViewById<TextView>(R.id.tag_txt)
        textView.text = categoryResponse?.name!!
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, UtilFun.getTextSize())
        categoryView.setOnClickListener {
            startActivity<TagActivity>(Utilities.ITEM_ID_BUNDLE to categoryResponse.id,
                Utilities.ITEM_TYPE_BUNDLE to Utilities.ITEM_CATEGORY,
                Utilities.ITEM_TITLE_BUNDLE to categoryResponse.name)

    }
        flowLayoutCategory.addView(categoryView)
    }

    private fun updateUI(status: String) {
        when (status) {
            Utilities.ERROR -> {
                progressBarPost.visibility = View.GONE
                linearLayoutError.visibility = View.VISIBLE
                linearLayoutPost.visibility = View.GONE
            }
            Utilities.LOADING -> {
                progressBarPost.visibility = View.VISIBLE
                linearLayoutError.visibility = View.GONE
                linearLayoutPost.visibility = View.GONE
            }

            Utilities.SUCCESS -> {
                progressBarPost.visibility = View.GONE
                linearLayoutError.visibility = View.GONE
                linearLayoutPost.visibility = View.VISIBLE
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key?.equals(getString(R.string.theme_color))!! || key == getString(R.string.text_scaling) ||
            key == getString(R.string.line_spacing)
        ) {
            recreate()
        }else if (key==getString(R.string.app_sites)||key == getString(R.string.input_site)
            ||key == getString(R.string.use_custom_site)){
            finish()
        }
    }
}
