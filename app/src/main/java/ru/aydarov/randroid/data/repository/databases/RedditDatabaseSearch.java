package ru.aydarov.randroid.data.repository.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.aydarov.randroid.data.model.RedditPost;

/**
 * @author Aydarov Askhar 2020
 */
@Database(entities = {RedditPost.class}, version = 1, exportSchema = false)
public abstract class RedditDatabaseSearch extends RoomDatabase {
    public abstract RedditSearchDao getDao();
}

