package ru.aydarov.randroid.data.repository.databases.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;
import ru.aydarov.randroid.data.model.Media;
import ru.aydarov.randroid.data.model.Preview;

/**
 * @author Aydarov Askhar 2020
 */
public class PostRedditConverter {
    public static class PreviewConverter {
        private Type mType = new TypeToken<Preview>() {
        }.getType();
        private Gson gson = new Gson();

        @TypeConverter
        public String save(Preview preview) {
            return gson.toJson(preview);
        }

        @TypeConverter
        public Preview restore(String data) {
            return gson.fromJson(data, mType);

        }
    }

    public static class VideoConverter {
        private Type mType = new TypeToken<Media>() {
        }.getType();
        private Gson gson = new Gson();

        @TypeConverter
        public String save(Media media) {
            return gson.toJson(media);
        }

        @TypeConverter
        public Media restore(String data) {
            return gson.fromJson(data, mType);

        }
    }



}
