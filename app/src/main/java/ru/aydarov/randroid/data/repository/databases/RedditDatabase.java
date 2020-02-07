package ru.aydarov.randroid.data.repository.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.aydarov.randroid.data.model.UserData;

/**
 * @author Aydarov Askhar 2020
 */
@Database(entities = {UserData.class}, version = 1)
public abstract class RedditDatabase extends RoomDatabase {
    public abstract RedditDao getDao();
}

