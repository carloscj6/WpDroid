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

data class Sizes(
    @SerializedName("full")
    var full: Full?,
    @SerializedName("large")
    var large: Large?,
    @SerializedName("medium")
    var medium: Medium?,
    @SerializedName("medium_large")
    var mediumLarge: MediumLarge?,
    @SerializedName("portum-blog-post-image")
    var portumBlogPostImage: PortumBlogPostImage?,
    @SerializedName("portum-blog-post-sticky")
    var portumBlogPostSticky: PortumBlogPostSticky?,
    @SerializedName("portum-blog-section-image")
    var portumBlogSectionImage: PortumBlogSectionImage?,
    @SerializedName("portum-main-slider")
    var portumMainSlider: PortumMainSlider?,
    @SerializedName("portum-portfolio-image")
    var portumPortfolioImage: PortumPortfolioImage?,
    @SerializedName("portum-team-image")
    var portumTeamImage: PortumTeamImage?,
    @SerializedName("thumbnail")
    var thumbnail: Thumbnail?,
    @SerializedName("woocommerce_gallery_thumbnail")
    var woocommerceGalleryThumbnail: WoocommerceGalleryThumbnail?,
    @SerializedName("woocommerce_single")
    var woocommerceSingle: WoocommerceSingle?,
    @SerializedName("woocommerce_thumbnail")
    var woocommerceThumbnail: WoocommerceThumbnail?
)