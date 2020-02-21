package ru.aydarov.randroid.presentation.di.app;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ru.aydarov.randroid.data.repository.databases.RedditDao;
import ru.aydarov.randroid.data.repository.databases.RedditDatabase;
import ru.aydarov.randroid.domain.util.TokensSharedHelper;
import ru.aydarov.randroid.presentation.common.App;

import static ru.aydarov.randroid.presentation.di.NamesUtil.TOKEN_PREF;
import static ru.aydarov.randroid.presentation.di.NamesUtil.USER_DATA_DATABASE_NAME;

/**
 * @author Aydarov Askhar 2020
 */

@Module
public class AppModule {

    private final App mApp;

    public AppModule(App mApp) {
        this.mApp = mApp;
    }

    @Provides
    @Singleton
    App provideApp() {
        return mApp;
    }

    @Provides
    @Singleton
    @Named(TOKEN_PREF)
    SharedPreferences getTokenPreference(App app) {
        return app.getSharedPreferences(TokensSharedHelper.TOKENS_PREF, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    RedditDatabase provideUserDatabase(App app) {
        return Room.databaseBuilder(app, RedditDatabase.class, USER_DATA_DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    RedditDao getUserDAO(RedditDatabase dataBase) {
        return dataBase.getDao();
    }


}
