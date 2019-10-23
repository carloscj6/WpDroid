package com.revosleap.wpdroid.utils.retrofit

import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetWpDataService {
    @GET("posts/{id}")
    fun getWpPost(@Path("id") postId: Long): Call<PostResponse>
}
