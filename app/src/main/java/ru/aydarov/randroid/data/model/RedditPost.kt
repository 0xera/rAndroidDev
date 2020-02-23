package ru.aydarov.randroid.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ru.aydarov.randroid.data.repository.databases.converter.PostRedditConverter
import ru.aydarov.randroid.data.util.RedditUtilsNet
import java.io.Serializable

/**
 * @author Aydarov Askhar 2020
 */
@Entity
@TypeConverters(PostRedditConverter.PreviewConverter::class,
        PostRedditConverter.VideoConverter::class)
data class RedditPost(
        @PrimaryKey
        @SerializedName("name")
        val name: String,
        @ColumnInfo(name = "id")
        @SerializedName("id")
        val id: String?,
        @SerializedName("author")
        @ColumnInfo(name = "author")
        val author: String,
        @SerializedName("created_utc")
        @ColumnInfo(name = "created_utc")
        val created: String,
        @SerializedName("is_self")
        @ColumnInfo(name = "is_self")
        val isSelf: Boolean,
        @SerializedName("is_video")
        @ColumnInfo(name = "is_video")
        val isVideo: Boolean,
        @SerializedName("post_hint")
        @ColumnInfo(name = "post_hint")
        val postHint: String?,
        @SerializedName("media")
        @ColumnInfo(name = "media")
        val media: Media?,
        @SerializedName("num_comments")
        @ColumnInfo(name = "num_comments")
        val numComments: Int,
        @SerializedName("preview")
        @ColumnInfo(name = "preview")
        val preview: Preview?,
        @SerializedName("score")
        @ColumnInfo(name = "score")
        val score: Int,
        @SerializedName("selftext")
        @ColumnInfo(name = "selftext")
        val selfText: String?,
        @SerializedName("selftext_html")
        @ColumnInfo(name = "selftext_html")
        val selfTextHtml: String?,
        @SerializedName("permalink")
        @ColumnInfo(name = "permalink")
        val permalink: String?,
        @SerializedName("thumbnail")
        @ColumnInfo(name = "thumbnail")
        val thumbnail: String,
        @SerializedName("title")
        @ColumnInfo(name = "title")
        val title: String,
        @SerializedName("ups")
        @ColumnInfo(name = "ups")
        val ups: Int,
        @SerializedName("url")
        @ColumnInfo(name = "url")
        val url: String

) : Serializable {
    var searchQuery: String? = null
    var indexInResponse = -1
    var sortType = RedditUtilsNet.HOT

    data class RedditChild(val data: RedditPost)

    data class RedditPostData(
            val children: List<RedditChild>,
            val after: String?,
            val before: String?
    )

    data class RedditPostResponse(val data: RedditPostData)
}





