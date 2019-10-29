package com.revosleap.wpdroid.ui.recyclerview.models.misc

import com.revosleap.wpdroid.ui.recyclerview.models.comment.CommentResponse

data class ParentComment(
    var comments: MutableList<CommentResponse>?,
    var comment: CommentResponse?
) {
    constructor() : this(null,null)
}