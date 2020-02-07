package ru.aydarov.randroid.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Aydarov Askhar 2020
 */
@Entity
data class RedditPost(
        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        val id: String,
        @ColumnInfo(name = "author")
        @SerializedName("author")
        val author: String,
        @ColumnInfo(name = "is_video")
        @SerializedName("is_video")
        val isVideo: Boolean,
        @ColumnInfo(name = "num_comments")
        @SerializedName("num_comments")
        val commentCount: Int,
        @ColumnInfo(name = "preview")
        @SerializedName("preview")
        val preview: Preview,
        @ColumnInfo(name = "thumbnail")
        @SerializedName("thumbnail")
        val thumbnail: String,
        @ColumnInfo(name = "title")
        @SerializedName("title")
        val title: String,
        @ColumnInfo(name = "score")
        @SerializedName("score")
        val score: Int,
        @ColumnInfo(name = "created_utc")
        @SerializedName("created_utc")
        val created: Long,
        @ColumnInfo(name = "url")
        @SerializedName("url")
        val url: String,
        @ColumnInfo(name = "likes")
        @SerializedName("likes")
        val likes: Boolean) {


    data class Preview(
            @SerializedName("enabled")
            val enabled: Boolean,
            @SerializedName("images")
            val images: List<Image>
    ) {
        data class Image(
                @SerializedName("id")
                val id: String,
                @SerializedName("source")
                val source: Source
        ) {

            data class Source(
                    @SerializedName("url")
                    val url: String
            )


        }
    }
}

data class RedditChild(val data: RedditPost)

data class RedditPostData(
        val children: List<RedditChild>,
        val after: String?,
        val before: String?
)

data class ReddtPostResponse(val data: RedditPostData)
