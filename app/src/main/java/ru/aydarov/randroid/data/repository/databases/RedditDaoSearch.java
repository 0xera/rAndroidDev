package ru.aydarov.randroid.data.repository.databases;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.model.RedditPostSearch;

/**
 * @author Aydarov Askhar 2020
 */
@Dao
public interface RedditDaoSearch {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RedditPostSearch> posts);


    @Query("DELETE FROM redditpostsearch")
    void deletePosts();

    @Query("SELECT MAX(indexInResponse) + 1 FROM redditpostsearch")
    int getNextIndexInSubreddit();

    @Query("SELECT * FROM redditpostsearch where searchQuery=:searchQuery")
    DataSource.Factory<Integer, RedditPost> getPosts(String searchQuery);

    @Query("SELECT MAX(indexInResponse) + 1 FROM redditpostsearch where searchQuery=:searchQuery")
    int getNextIndexInSubreddit(String searchQuery);
}
