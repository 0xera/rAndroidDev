package ru.aydarov.randroid.data.repository.databases.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;
import ru.aydarov.randroid.data.model.RedditPost;

/**
 * @author Aydarov Askhar 2020
 */
public class PostRedditConverter {
    public static class PreviewConverter {
        private Type mType = new TypeToken<RedditPost.Preview>() {
        }.getType();
        private Gson gson = new Gson();

        @TypeConverter
        public String save(RedditPost.Preview preview) {
            return gson.toJson(preview);
        }

        @TypeConverter
        public RedditPost.Preview restore(String data) {
            return gson.fromJson(data, mType);

        }
    }

    public static class VideoConverter {
        private Type mType = new TypeToken<RedditPost.Media>() {
        }.getType();
        private Gson gson = new Gson();

        @TypeConverter
        public String save(RedditPost.Media media) {
            return gson.toJson(media);
        }

        @TypeConverter
        public RedditPost.Media restore(String data) {
            return gson.fromJson(data, mType);

        }
    }



}
