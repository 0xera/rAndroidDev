package ru.aydarov.randroid.presentation.common;

import android.app.Application;

import ru.aydarov.randroid.data.util.LiveConnectUtil;
import ru.aydarov.randroid.presentation.di.AppComponent;
import ru.aydarov.randroid.presentation.di.AppModule;
import ru.aydarov.randroid.presentation.di.DaggerAppComponent;
import ru.aydarov.randroid.presentation.di.NetworkModule;

/**
 * @author Aydarov Askhar 2020
 */
public class App extends Application {
    private static AppComponent sAppComponent;

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
