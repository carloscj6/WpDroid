package com.revosleap.wpdroid.ui.recyclerview.itemViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.activities.ScrollingActivity
import com.revosleap.wpdroid.ui.recyclerview.models.media.MediaResponse
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.utils.misc.Utilities
import com.revosleap.wpdroid.utils.retrofit.GetWpDataService
import com.revosleap.wpdroid.utils.retrofit.RetrofitClient
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.sufficientlysecure.htmltextview.HtmlTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemViewBlog : ItemViewBinder<PostResponse, ItemViewBlog.BlogItemView>(),AnkoLogger {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BlogItemView {
        return BlogItemView(inflater.inflate(R.layout.item_posts, parent, false))
    }
    override fun onBindViewHolder(holder: BlogItemView, item: PostResponse) {
        holder.bind(item)

    }

    class BlogItemView(itemView: View) : RecyclerView.ViewHolder(itemView),AnkoLogger {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewPost)
        val title: HtmlTextView = itemView.findViewById(R.id.textViewPostTitle)
        fun bind(postResponse: PostResponse) {
            title.setHtml(postResponse.title?.rendered!!)
            getImage(postResponse.featuredMedia!!)
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
    //https://www.tecmint.com/wp-json/wp/v2/categories/408
    //https://www.tecmint.com/wp-json/wp/v2/categories/687
}