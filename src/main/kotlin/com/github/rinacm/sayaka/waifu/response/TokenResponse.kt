package com.github.rinacm.sayaka.waifu.response

import com.google.gson.annotations.SerializedName

data class TokenResponse(val response: Response) {
    data class Response(
        @SerializedName("access_token")
        val accessToken: String,

        @SerializedName("expires_in")
        val expiresIn: Long,

        @SerializedName("token_type")
        val tokenType: String,

        val scope: String,

        @SerializedName("refresh_token")
        val refreshToken: String,

        val user: User,

        @SerializedName("device_token")
        val deviceToken: String
    )

    data class User(
        @SerializedName("profile_image_urls")
        val profileImageUrls: ProfileImageUrls,

        val id: String,
        val name: String,
        val account: String,

        @SerializedName("mail_address")
        val mailAddress: String,

        @SerializedName("is_premium")
        val isPremium: Boolean,

        @SerializedName("x_restrict")
        val xRestrict: Long,

        @SerializedName("is_mail_authorized")
        val isMailAuthorized: Boolean
    )

    data class ProfileImageUrls(
        @SerializedName("px_16x16")
        val px16X16: String,

        @SerializedName("px_50x50")
        val px50X50: String,

        @SerializedName("px_170x170")
        val px170X170: String
    )
}