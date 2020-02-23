package ru.aydarov.randroid.presentation.di.search;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ru.aydarov.randroid.data.repository.databases.RedditDatabaseSearch;
import ru.aydarov.randroid.data.repository.databases.RedditSearchDao;
import ru.aydarov.randroid.presentation.common.App;

import static ru.aydarov.randroid.presentation.di.NamesUtil.SEARCH_DATA_DATABASE_NAME;

/**
 * @author Aydarov Askhar 2020
 */

@Module
public class AppSearchModule {


    @Provides
    @SearchScope
    RedditDatabaseSearch provideSearchDatabase(App app) {
        return Room.databaseBuilder(app, RedditDatabaseSearch.class, SEARCH_DATA_DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @SearchScope
    RedditSearchDao getSearchDAO(RedditDatabaseSearch dataBase) {
        return dataBase.getDao();
    }


}
