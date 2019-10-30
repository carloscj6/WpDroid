package com.revosleap.wpdroid.utils.callbacks

import com.revosleap.wpdroid.ui.recyclerview.models.tags.TagResponse

interface TagSelected {
    fun onTagSelected(tagResponse: TagResponse)
}