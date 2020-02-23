package ru.aydarov.randroid.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author Aydarov Askhar 2020
 */
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


