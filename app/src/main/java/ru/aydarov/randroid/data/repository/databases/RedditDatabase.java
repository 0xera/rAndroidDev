package ru.aydarov.randroid.data.repository.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.model.UserData;

/**
 * @author Aydarov Askhar 2020
 */
@Database(entities = {UserData.class, RedditPost.class}, version = 25, exportSchema = false)
public abstract class RedditDatabase extends RoomDatabase {
    public abstract RedditDao getDao();
}

