/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 *  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.ui.recyclerview.models.post


import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("author")
    var author: Long?,
    @SerializedName("categories")
    var categories: List<Long?>?,
    @SerializedName("comment_status")
    var commentStatus: String?,
    @SerializedName("content")
    var content: Content?,
    @SerializedName("date")
    var date: String?,
    @SerializedName("date_gmt")
    var dateGmt: String?,
    @SerializedName("excerpt")
    var excerpt: Excerpt?,
    @SerializedName("featured_media")
    var featuredMedia: Long?,
    @SerializedName("format")
    var format: String?,
    @SerializedName("guid")
    var guid: Guid?,
    @SerializedName("id")
    var id: Long?,
    @SerializedName("link")
    var link: String?,
    @SerializedName("_links")
    var links: Links?,
    @SerializedName("modified")
    var modified: String?,
    @SerializedName("modified_gmt")
    var modifiedGmt: String?,
    @SerializedName("ping_status")
    var pingStatus: String?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("sticky")
    var sticky: Boolean?,
    @SerializedName("tags")
    var tags: List<Long?>?,
    @SerializedName("template")
    var template: String?,
    @SerializedName("title")
    var title: Title?,
    @SerializedName("type")
    var type: String?
) {
    constructor() : this(null,null,null,null,null,null,
        null,null,null,null,null,null,null,
        null,null,null,null,null,null,null,
        null,null,null)
}