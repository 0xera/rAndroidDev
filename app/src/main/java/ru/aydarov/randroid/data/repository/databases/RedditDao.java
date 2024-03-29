package ru.aydarov.randroid.data.repository.databases;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.model.UserData;

/**
 * @author Aydarov Askhar 2020
 */
@Dao
public interface RedditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserData userData);

    @Query("SELECT * FROM UserData")
    Flowable<UserData> getUserData();

    @Query("DELETE FROM UserData WHERE name =:username")
    void deleteUser(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RedditPost> posts);


    @Query("DELETE FROM redditpost")
    void deletePosts();


    @Query("SELECT MAX(indexInResponse) + 1 FROM redditpost")
    int getNextIndexInSubreddit();

    @Query("SELECT * FROM redditpost")
    DataSource.Factory<Integer, RedditPost> getPosts();
}
