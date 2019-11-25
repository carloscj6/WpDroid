package com.revosleap.wpdroid.ui.recyclerview.itemViews

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.activities.ArticleActivity
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
import java.text.SimpleDateFormat
import java.util.*

class ItemViewBlog : ItemViewBinder<PostResponse, ItemViewBlog.BlogItemView>(), AnkoLogger {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BlogItemView {
        return BlogItemView(inflater.inflate(R.layout.item_posts, parent, false))
    }

    override fun onBindViewHolder(holder: BlogItemView, item: PostResponse) {
        holder.bind(item)

    }

    class BlogItemView(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewPost)
        val title: TextView = itemView.findViewById(R.id.textViewPostTitle)
        val info: TextView = itemView.findViewById(R.id.textViewTimeInfo)

        fun bind(postResponse: PostResponse) {
            val titleText=postResponse.title?.rendered!!
            getImage(postResponse.featuredMedia!!)
            itemView.setOnClickListener {
                itemView.context.startActivity<ArticleActivity>(Utilities.BLOG_ID to postResponse.id)
            }
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val sdfInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val date = sdfInput.parse(postResponse.dateGmt)
            info.text = sdf.format(date)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                title.text = Html.fromHtml(titleText, Html.FROM_HTML_MODE_COMPACT)
            } else title.text = Html.fromHtml(titleText)
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
                    Picasso.get().load(response.body()?.sourceUrl)
                        .placeholder(R.drawable.blog_item_placeholder)
                        .into(imageView)

                }
            })
        }
    }

}