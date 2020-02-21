package ru.aydarov.randroid.data.model

import com.google.gson.annotations.SerializedName
import ru.aydarov.randroid.data.util.RedditUtilsNet

/**
 * @author Aydarov Askhar 2020
 */
data class Token(
        @SerializedName(RedditUtilsNet.ACCESS_TOKEN_KEY) val accessToken: String,
        @SerializedName(RedditUtilsNet.REFRESH_TOKEN_KEY) val refreshToken: String
)
