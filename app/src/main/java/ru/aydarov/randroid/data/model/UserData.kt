package ru.aydarov.randroid.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ru.aydarov.randroid.data.repository.databases.converter.UserSubRedditConverter
import ru.aydarov.randroid.data.util.RedditUtils

/**
 * @author Aydarov Askhar 2020
 */
@Entity
@TypeConverters(UserSubRedditConverter::class)
data class UserData(@PrimaryKey @ColumnInfo(name = RedditUtils.NAME_KEY) @SerializedName(RedditUtils.NAME_KEY) val name: String,
                    @ColumnInfo(name = RedditUtils.ICON_IMG_KEY) @SerializedName(RedditUtils.ICON_IMG_KEY) val icon: String,
                   @ColumnInfo(name = RedditUtils.SUBREDDIT_KEY) @SerializedName(RedditUtils.SUBREDDIT_KEY) val sub: UserSubReddit) {
    data class UserSubReddit(@ColumnInfo(name = RedditUtils.BANNER_IMG_KEY) @SerializedName(RedditUtils.BANNER_IMG_KEY) val banner: String,
                             @ColumnInfo(name = RedditUtils.DESCRIPTION_KEY) @SerializedName(RedditUtils.DESCRIPTION_KEY) val description: String)
}

