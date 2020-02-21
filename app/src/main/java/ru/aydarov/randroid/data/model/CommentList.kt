package ru.aydarov.randroid.data.model


import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CommentList(
        @SerializedName("data")
        val data: Data?,
        @SerializedName("kind")
        val kind: String
) : Serializable {
    data class Data(
            @SerializedName("children")
            val comments: List<Comment>?,
            @SerializedName("dist")
            val dist: Int
    ) : Serializable

}

data class Comment(
        @SerializedName("data")
        val data: CommentData,
        @SerializedName("kind")
        val kind: String
) : Serializable {
    data class CommentData(
            @PrimaryKey
            @SerializedName("name")
            val name: String,
            @SerializedName("author")
            val author: String,
            @SerializedName("body")
            val body: String,
            @SerializedName("body_html")
            val bodyHtml: String,
            @SerializedName("collapsed")
            val collapsed: Boolean,
            @SerializedName("created_utc")
            val createdUtc: Double,
            @SerializedName("depth")
            val depth: Int,
            @SerializedName("edited")
            val edited: Boolean,
            @SerializedName("id")
            val id: String,
            @SerializedName("parent_id")
            val parentId: String,
            @SerializedName("permalink")
            val permalink: String,
            @SerializedName("replies")
            val replies: Replies

    ) : Serializable {

        data class Replies(
                @SerializedName("data")
                val data: CommentList.Data,
                @SerializedName("kind")
                val kind: String
        ) : Serializable
    }
}