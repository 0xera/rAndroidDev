package ru.aydarov.randroid.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ru.aydarov.randroid.data.repository.databases.converter.UserSubRedditConverter
import ru.aydarov.randroid.data.util.RedditUtilsNet

/**
 * @author Aydarov Askhar 2020
 */
@Entity
@TypeConverters(UserSubRedditConverter::class)
data class UserData(
        @PrimaryKey @ColumnInfo(name = RedditUtilsNet.NAME_KEY) @SerializedName(RedditUtilsNet.NAME_KEY) val name: String,
        @ColumnInfo(name = RedditUtilsNet.ICON_IMG_KEY) @SerializedName(RedditUtilsNet.ICON_IMG_KEY) val icon: String,
        @ColumnInfo(name = RedditUtilsNet.SUBREDDIT_KEY) @SerializedName(RedditUtilsNet.SUBREDDIT_KEY) val sub: UserSubReddit) {
    data class UserSubReddit(@ColumnInfo(name = RedditUtilsNet.BANNER_IMG_KEY) @SerializedName(RedditUtilsNet.BANNER_IMG_KEY) val banner: String,
                             @ColumnInfo(name = RedditUtilsNet.DESCRIPTION_KEY) @SerializedName(RedditUtilsNet.DESCRIPTION_KEY) val description: String){
    }
}

