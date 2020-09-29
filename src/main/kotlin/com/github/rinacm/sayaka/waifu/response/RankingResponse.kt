package com.github.rinacm.sayaka.waifu.response

import com.github.rinacm.sayaka.waifu.model.Illustration
import com.google.gson.annotations.SerializedName

data class RankingResponse(
    val illusts: List<Illust>,

    @SerializedName("next_url")
    val nextURL: String
) {
    data class Illust(
        val id: Long,
        val title: String,
        val type: String,

        @SerializedName("image_urls")
        val imageUrls: ImageUrls,

        val caption: String,
        val restrict: Long,
        val user: User,
        val tags: List<Tag>,
        val tools: List<String>,

        @SerializedName("create_date")
        val createDate: String,

        @SerializedName("page_count")
        val pageCount: Long,

        val width: Long,
        val height: Long,

        @SerializedName("sanity_level")
        val sanityLevel: Long,

        @SerializedName("x_restrict")
        val xRestrict: Long,

        val series: Series? = null,

        @SerializedName("meta_single_page")
        val metaSinglePage: MetaSinglePage,

        @SerializedName("meta_pages")
        val metaPages: List<MetaPage>,

        @SerializedName("total_view")
        val totalView: Long,

        @SerializedName("total_bookmarks")
        val totalBookmarks: Long,

        @SerializedName("is_bookmarked")
        val isBookmarked: Boolean,

        val visible: Boolean,

        @SerializedName("is_muted")
        val isMuted: Boolean
    ) {
        fun parse(): Illustration {
            return Illustration(
                id.toString(),
                metaSinglePage.originalImageURL ?: imageUrls.large,
                imageUrls.medium,
                totalBookmarks.toInt(),
                title,
                user.name,
                user.id.toString(),
                tags.map(Tag::name),
                createDate,
                totalView.toInt()
            )
        }
    }

    data class ImageUrls(
        @SerializedName("square_medium")
        val squareMedium: String,

        val medium: String,
        val large: String,
        val original: String? = null
    )

    data class MetaPage(
        @SerializedName("image_urls")
        val imageUrls: ImageUrls
    )

    data class MetaSinglePage(
        @SerializedName("original_image_url")
        val originalImageURL: String? = null
    )

    data class Series(
        val id: Long,
        val title: String
    )

    data class Tag(
        val name: String
    )

    data class User(
        val id: Long,
        val name: String,
        val account: String,

        @SerializedName("profile_image_urls")
        val profileImageUrls: ProfileImageUrls,

        @SerializedName("is_followed")
        val isFollowed: Boolean
    )

    data class ProfileImageUrls(
        val medium: String
    )
}