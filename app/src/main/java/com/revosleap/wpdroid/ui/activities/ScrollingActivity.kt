package com.revosleap.wpdroid.ui.activities

import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.models.category.CategoryResponse
import com.revosleap.wpdroid.ui.recyclerview.models.media.MediaResponse
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.ui.recyclerview.models.tags.TagResponse
import com.revosleap.wpdroid.utils.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import org.jetbrains.anko.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class ScrollingActivity : AppCompatActivity(), AnkoLogger {
    private var wpDataService: GetWpDataService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        wpDataService =
            RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        getPost()
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

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getPost() {
        val postId = intent.getLongExtra(Utilities.BLOG_ID, 0)
        val call = wpDataService?.getWpPost(postId)
        val progDialog = indeterminateProgressDialog("Please wait...", "Getting Post")
        progDialog.show()
        call?.enqueue(object : Callback<PostResponse> {
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                progDialog.dismiss()
                Toast.makeText(this@ScrollingActivity, t.message, Toast.LENGTH_SHORT).show()
                alert(t.message!!) {
                    yesButton { getPost() }
                    noButton { finish() }
                }.show()

            }

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val post = response.body()
                textViewPost.setHtml(post?.content?.rendered!!, HtmlHttpImageGetter(textViewPost))
                blogTitle.setHtml(post.title!!.rendered!!)
                textViewTitle.setHtml(post.title!!.rendered!!)
                textViewAuthor.setHtml("Carlos")
                getMedia(post.featuredMedia!!)
                progDialog.dismiss()
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
            }

        })
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
                Picasso.with(this@ScrollingActivity).load(response.body()?.sourceUrl)
                    .placeholder(R.drawable.blog_placeholder).into(imageViewHeader)

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
                warn(call.request().url().toString())
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

    fun setTagView(categoryResponse: TagResponse?) {
        val view = findViewById<View>(android.R.id.content) as ViewGroup
        val categoryView = layoutInflater.inflate(R.layout.item_tag, view, false)
        val textView = categoryView.findViewById<TextView>(R.id.textViewHashTag)
        val frameLayoutTAG= categoryView.findViewById<FrameLayout>(R.id.frameLayoutTag)
        frameLayoutTAG.layoutParams.width= FrameLayout.LayoutParams.WRAP_CONTENT
        val hashTag= "#${categoryResponse?.name}"
        textView.paintFlags= Paint.UNDERLINE_TEXT_FLAG
        textView.text = hashTag
        flowLayoutTags.addView(categoryView)
    }

    fun setCategoryView(categoryResponse: CategoryResponse?) {
        val view = findViewById<View>(android.R.id.content) as ViewGroup
        val categoryView = layoutInflater.inflate(R.layout.category_item, view, false)
        val textView = categoryView.findViewById<TextView>(R.id.tag_txt)
        textView.text = categoryResponse?.name!!
        warn(categoryResponse.name!!)
        flowLayoutCategory.addView(categoryView)
    }
}
