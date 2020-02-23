package ru.aydarov.randroid.data.repository.databases;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import ru.aydarov.randroid.data.model.RedditPost;

/**
 * @author Aydarov Askhar 2020
 */
@Dao
public interface RedditSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RedditPost> posts);


    @Query("DELETE FROM redditpost")
    void deletePosts();

    @Query("SELECT MAX(indexInResponse) + 1 FROM redditpost")
    int getNextIndexInSubreddit();

    @Query("SELECT * FROM redditpost where searchQuery=:searchQuery")
    DataSource.Factory<Integer, RedditPost> getPosts(String searchQuery);

    @Query("SELECT MAX(indexInResponse) + 1 FROM redditpost where searchQuery=:searchQuery")
    int getNextIndexInSubreddit(String searchQuery);
}
