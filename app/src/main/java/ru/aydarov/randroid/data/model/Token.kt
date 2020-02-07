package ru.aydarov.randroid.data.model

import com.google.gson.annotations.SerializedName
import ru.aydarov.randroid.data.util.RedditUtils

/**
 * @author Aydarov Askhar 2020
 */
data class Token(@SerializedName(RedditUtils.ACCESS_TOKEN_KEY) val accessToken: String,
                 @SerializedName(RedditUtils.REFRESH_TOKEN_KEY) val refreshToken: String
)
