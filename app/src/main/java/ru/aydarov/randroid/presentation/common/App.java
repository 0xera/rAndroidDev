package ru.aydarov.randroid.presentation.common;

import android.app.Application;

import ru.aydarov.randroid.presentation.di.app.AppComponent;
import ru.aydarov.randroid.presentation.di.app.AppModule;
import ru.aydarov.randroid.presentation.di.app.DaggerAppComponent;
import ru.aydarov.randroid.presentation.di.app.NetworkModule;
import ru.aydarov.randroid.presentation.di.search.AppSearchModule;
import ru.aydarov.randroid.presentation.di.search.InteractorSearchModule;
import ru.aydarov.randroid.presentation.di.search.NetworkSearchModule;
import ru.aydarov.randroid.presentation.di.search.RepositorySearchModule;
import ru.aydarov.randroid.presentation.di.search.SearchComponent;
import ru.aydarov.randroid.presentation.util.LiveConnectUtil;

/**
 * @author Aydarov Askhar 2020
 */
public class App extends Application {
    private static AppComponent sAppComponent;
    private static SearchComponent sSearchComponent;

    public static SearchComponent getSearchComponent() {
        if (sSearchComponent == null) {
            sSearchComponent = sAppComponent.searchComponentBuilder()
                    .appModule(new AppSearchModule())
                    .networkModule(new NetworkSearchModule())
                    .repositoryModule(new RepositorySearchModule())
                    .interactorModule(new InteractorSearchModule())
                    .build();
        }
        return sSearchComponent;
    }

    public static void clearSearchComponent() {
        sSearchComponent = null;
    }

    @Override
    public void onCreate() {
        LiveConnectUtil.getInstance().init(this);
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
        super.onCreate();
    }


    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
