/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.ui.recyclerview.models.tags


import com.google.gson.annotations.SerializedName

data class TagResponse(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id:Long?,
    @SerializedName("link")
    var link: String?,
    @SerializedName("_links")
    var links: Links?,
    @SerializedName("meta")
    var meta: List<Any?>?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("taxonomy")
    var taxonomy: String?
)