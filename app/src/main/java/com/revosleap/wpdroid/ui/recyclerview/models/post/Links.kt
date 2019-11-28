/*
 * Copyright (c) 2019. (Carlos)
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.revosleap.wpdroid.ui.recyclerview.models.post


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("about")
    var about: List<About?>?,
    @SerializedName("author")
    var author: List<Author?>?,
    @SerializedName("collection")
    var collection: List<Collection?>?,
    @SerializedName("curies")
    var curies: List<Cury?>?,
    @SerializedName("predecessor-version")
    var predecessorVersion: List<PredecessorVersion?>?,
    @SerializedName("replies")
    var replies: List<Reply?>?,
    @SerializedName("self")
    var self: List<Self?>?,
    @SerializedName("version-history")
    var versionHistory: List<VersionHistory?>?,
    @SerializedName("wp:attachment")
    var wpAttachment: List<WpAttachment?>?,
    @SerializedName("wp:featuredmedia")
    var wpFeaturedmedia: List<WpFeaturedmedia?>?,
    @SerializedName("wp:term")
    var wpTerm: List<WpTerm?>?
)