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