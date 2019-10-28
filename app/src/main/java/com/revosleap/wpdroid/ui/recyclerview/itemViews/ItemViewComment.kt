package com.revosleap.wpdroid.ui.recyclerview.itemViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.revosleap.wpdroid.R
import com.revosleap.wpdroid.ui.recyclerview.models.comment.CommentResponse
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.sufficientlysecure.htmltextview.HtmlTextView
import java.text.SimpleDateFormat
import java.util.*

class ItemViewComment:ItemViewBinder<CommentResponse,ItemViewComment.CommentHolder>() {
    override fun onBindViewHolder(holder: CommentHolder, item: CommentResponse) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): CommentHolder {
        return CommentHolder(inflater.inflate(R.layout.item_parent_comment,parent,false))
    }

    class CommentHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val imageView= itemView.findViewById<CircleImageView>(R.id.circleImageViewComment)
        private val commentTextView= itemView.findViewById<HtmlTextView>(R.id.texViewComment)
        private val commentAuthor= itemView.findViewById<TextView>(R.id.textViewCommentAuthor)
        private val commentDate= itemView.findViewById<TextView>(R.id.textViewCommentDate)
        fun bind(commentResponse: CommentResponse){
            Picasso.with(itemView.context).load(commentResponse.authorAvatarUrls?.x96).into(imageView)
            commentTextView.setHtml(commentResponse.content?.rendered!!)
            commentAuthor.text= commentResponse.authorName
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val sdfInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val date = sdfInput.parse(commentResponse.dateGmt!!)
            commentDate.text= sdf.format(date)

        }
    }
}