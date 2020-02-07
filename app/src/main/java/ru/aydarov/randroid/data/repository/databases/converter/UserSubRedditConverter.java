package ru.aydarov.randroid.data.repository.databases.converter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;
import ru.aydarov.randroid.data.model.UserData;


public class UserSubRedditConverter {

    private Type mType = new TypeToken<UserData.UserSubReddit>() {}.getType();
    private Gson gson = new Gson();

    @TypeConverter
    public String save(UserData.UserSubReddit userSubReddit) {
        return gson.toJson(userSubReddit);
    }

    @TypeConverter
    public UserData.UserSubReddit restore(String data) {
        return gson.fromJson(data, mType);

    }

}
