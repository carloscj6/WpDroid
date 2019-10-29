package com.revosleap.wpdroid.ui.recyclerview.itemViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.components.WpDroidAdapter
import com.revosleap.wpdroid.ui.recyclerview.models.misc.ParentComment
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import org.sufficientlysecure.htmltextview.HtmlTextView
import java.text.SimpleDateFormat
import java.util.*

class ItemViewComment : ItemViewBinder<ParentComment, ItemViewComment.CommentHolder>() {
    override fun onBindViewHolder(holder: CommentHolder, item: ParentComment) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): CommentHolder {
        return CommentHolder(inflater.inflate(R.layout.item_parent_comment, parent, false))
    }

    class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView),AnkoLogger {
        private val imageView = itemView.findViewById<CircleImageView>(R.id.circleImageViewComment)
        private val commentTextView = itemView.findViewById<HtmlTextView>(R.id.texViewComment)
        private val commentAuthor = itemView.findViewById<TextView>(R.id.textViewCommentAuthor)
        private val commentDate = itemView.findViewById<TextView>(R.id.textViewCommentDate)
        private val childComments= itemView.findViewById<RecyclerView>(R.id.recyclerViewChildComments)
        fun bind(commentResponse: ParentComment) {
            val comment = commentResponse.comment
            warn(commentResponse.comments?.size.toString())
            Picasso.with(itemView.context).load(comment?.authorAvatarUrls?.x96)
                .placeholder(R.drawable.author_placehoder).into(imageView)
            commentTextView?.setHtml(comment?.content?.rendered?.toString().toString())
            commentAuthor?.text = comment?.authorName
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val sdfInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            try {
                val date = sdfInput.parse(comment?.dateGmt)
                commentDate?.text = sdf.format(date)
                loadComments(commentResponse)
            }catch (e:Exception){
                e.printStackTrace()
            }


        }
        private fun loadComments(commentResponse: ParentComment){
            val childAdapter= WpDroidAdapter()
            childAdapter.register(ItemVewChildComment())
            childComments.apply {
                adapter= childAdapter
                layoutManager= LinearLayoutManager(this.context)
                hasFixedSize()
            }
           childAdapter.addItems(commentResponse.comments!!)
        }
    }
}