package com.revosleap.wpdroid.utils.retrofit

import com.revosleap.wpdroid.ui.recyclerview.models.media.MediaResponse
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetWpDataService {
    @GET("posts/{id}")
    fun getWpPost(@Path("id") postId: Long): Call<PostResponse>

    @GET("media/{id}")
    fun getWpMedia(@Path("id") mediaId: Long): Call<MediaResponse>

    @GET("posts/")
    fun getWpPosts(@Query("per_page")postCount:Int,@Query("page")page:Long):Call<List<PostResponse>>
}
