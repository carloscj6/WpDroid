package com.revosleap.wpdroid.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.models.media.MediaResponse
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.utils.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import org.jetbrains.anko.*
import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import org.sufficientlysecure.htmltextview.HtmlResImageGetter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
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
        val wpDataService =
            RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
        val postId= intent.getLongExtra(Utilities.BLOG_ID,0)
        val call = wpDataService?.getWpPost(postId)
        val progDialog= indeterminateProgressDialog("Please wait...","Getting Post")
        progDialog.show()
        call?.enqueue(object : Callback<PostResponse> {
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                progDialog.dismiss()
                Toast.makeText(this@ScrollingActivity, t.message, Toast.LENGTH_SHORT).show()
               alert (t.message!!)  {
                    yesButton { getPost() }
                    noButton { finish() }
                }.show()


            }

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val post = response.body()
                textViewPost.setHtml(post?.content?.rendered!!, HtmlHttpImageGetter(textViewPost))
                blogTitle.setHtml(post.title!!.rendered!!)
                getMedia(post.featuredMedia!!)
                progDialog.dismiss()
            }

        })
    }

    private fun getMedia(id:Long){
        val wpDataService =
                RetrofitClient.getRetrofitInstance()?.create(GetWpDataService::class.java)
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
}
