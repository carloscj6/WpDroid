/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.utils.retrofit

import com.revosleap.wpdroid.ui.recyclerview.models.category.CategoryResponse
import com.revosleap.wpdroid.ui.recyclerview.models.comment.CommentResponse
import com.revosleap.wpdroid.ui.recyclerview.models.media.MediaResponse
import com.revosleap.wpdroid.ui.recyclerview.models.post.PostResponse
import com.revosleap.wpdroid.ui.recyclerview.models.tags.TagResponse
import com.revosleap.wpdroid.ui.recyclerview.models.user.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetWpDataService {
    @GET("posts/{id}")
    fun getWpPost(@Path("id") postId: Long): Call<PostResponse>

    @GET("media/{id}")
    fun getWpMedia(@Path("id") mediaId: Long): Call<MediaResponse>

    @GET("categories/{id}")
    fun getWpCategory(@Path("id") categoryId: Long): Call<CategoryResponse>

    @GET("tags/{id}")
    fun getWpTag(@Path("id") categoryId: Long): Call<TagResponse>

    @GET("users/{id}")
    fun getWpUser(@Path("id") userId: Long): Call<UserResponse>

    @GET("comments")
    fun getWpPostComments(@Query("post")postId:Long, @Query("per_page")
    commentCount:Int,@Query("page")page:Long):Call<List<CommentResponse>>

    @GET("posts/")
    fun getWpPosts(
        @Query(
            "per_page"
        ) postCount: Int, @Query("page") page: Long
    ): Call<List<PostResponse>>

    @GET("posts/")
    fun getWpPostsCategorized(
        @Query("categories") categoryId: Long, @Query("per_page") postCount: Int, @Query(
            "page"
        ) page: Long
    ): Call<List<PostResponse>>

    @GET("posts/")
    fun getAuthorPosts(@Query("author")authorId:Long,@Query("per_page")postCount: Int,
    @Query("page")authorPage:Long):Call<List<PostResponse>>

    @GET("posts/")
    fun getWptTagPosts(@Query("tags")tagId:Long,@Query("per_page")postCount: Int,
                       @Query("page")tagPage: Long):Call<List<PostResponse>>

    @GET("tags/")
    fun getWpTags(
        @Query(
            "per_page"
        ) tagCount: Int, @Query("page") tagPage: Long
    ): Call<List<TagResponse>>

    @GET("categories/")
    fun getWpCategories(
        @Query("per_page") categoryCount: Int, @Query("page") categoryPage: Long,
     @Query("hide_empty")hideEmpty:Boolean): Call<List<CategoryResponse>>

}
