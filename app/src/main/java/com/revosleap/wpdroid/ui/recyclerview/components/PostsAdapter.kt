package com.revosleap.wpdroid.ui.recyclerview.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.activities.ScrollingActivity
import com.revosleap.wpdroid.ui.recyclerview.models.media.MediaResponse
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.utils.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.warn
import org.sufficientlysecure.htmltextview.HtmlTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsAdapter:RecyclerView.Adapter<PostsAdapter.BlogItemView>() {
    private val posts= mutableListOf<PostResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogItemView {
        return BlogItemView(LayoutInflater.from(parent.context).inflate(R.layout.item_posts,parent,false))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun addArticles(items:List<PostResponse>){
        posts.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BlogItemView, position: Int) {
       holder.bind(posts[position])
    }

    class BlogItemView(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewPost)
        val title: HtmlTextView = itemView.findViewById(R.id.textViewPostTitle)
        fun bind(postResponse: PostResponse) {
            title.setHtml(postResponse.title!!.rendered!!)
            getImage(postResponse.featuredMedia!!)
            warn(postResponse.title)
            itemView.setOnClickListener {
                itemView.context.startActivity<ScrollingActivity>(Utilities.BLOG_ID to postResponse.id)
            }
        }

        private fun getImage(id: Long) {
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
                    Picasso.with(itemView.context).load(response.body()?.sourceUrl)
                        .placeholder(R.drawable.blog_item_placeholder)
                        .into(imageView)

                }
            })
        }
    }
}