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

package com.revosleap.wpdroid.ui.recyclerview.models.media


import com.google.gson.annotations.SerializedName

data class ImageMeta(
    @SerializedName("aperture")
    var aperture: String?,
    @SerializedName("camera")
    var camera: String?,
    @SerializedName("caption")
    var caption: String?,
    @SerializedName("copyright")
    var copyright: String?,
    @SerializedName("created_timestamp")
    var createdTimestamp: String?,
    @SerializedName("credit")
    var credit: String?,
    @SerializedName("focal_length")
    var focalLength: String?,
    @SerializedName("iso")
    var iso: String?,
    @SerializedName("keywords")
    var keywords: List<Any?>?,
    @SerializedName("orientation")
    var orientation: String?,
    @SerializedName("shutter_speed")
    var shutterSpeed: String?,
    @SerializedName("title")
    var title: String?
)