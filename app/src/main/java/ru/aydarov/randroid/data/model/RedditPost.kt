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

    var indexInResponse = -1
    var sortType = RedditUtilsNet.HOT
    var searchQuery: String? = null

    data class Media(
            @SerializedName("reddit_video")
            val video: Video?
    ) : Serializable {
        data class Video(
                @SerializedName("dash_url")
                val dashUrl: String,
                @SerializedName("duration")
                val duration: Int,
                @SerializedName("fallback_url")
                val fallback: String,
                @SerializedName("hls_url")
                val hlsUrl: String,
                @SerializedName("is_gif")
                val isGif: Boolean,
                @SerializedName("scrubber_media_url")
                val scrubberMediaUrl: String,
                @SerializedName("transcoding_status")
                val transcodingStatus: String,
                @SerializedName("width")
                val width: Int
        ) : Serializable
    }

    data class Preview(
            @SerializedName("enabled")
            @ColumnInfo(name = "enabled")
            val enabled: Boolean,
            @SerializedName("images")
            @ColumnInfo(name = "images")
            val images: List<Image>?
    ) : Serializable {
        data class Image(
                @SerializedName("id")
                @ColumnInfo(name = "id")
                val id: String,
                @SerializedName("resolutions")
                val resolutions: List<Resolution>?,
                @SerializedName("source")
                @ColumnInfo(name = "source")
                val source: Source?
        ) : Serializable {
            data class Resolution(
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("url")
                    val url: String?,
                    @SerializedName("width")
                    val width: Int
            ) : Serializable

            data class Source(
                    @SerializedName("height")
                    @ColumnInfo(name = "height")
                    val height: Int,
                    @SerializedName("url")
                    @ColumnInfo(name = "url")
                    val url: String?,
                    @SerializedName("width")
                    @ColumnInfo(name = "width")
                    val width: Int
            ) : Serializable

        }
    }

}

data class RedditChild(val data: RedditPost)

data class RedditPostData(
        val children: List<RedditChild>,
        val after: String?,
        val before: String?
)

data class RedditPostResponse(val data: RedditPostData)
