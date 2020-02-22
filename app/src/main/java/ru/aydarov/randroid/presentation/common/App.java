package ru.aydarov.randroid.presentation.common;

import android.app.Application;

import ru.aydarov.randroid.presentation.di.app.AppComponent;
import ru.aydarov.randroid.presentation.di.app.AppModule;
import ru.aydarov.randroid.presentation.di.app.DaggerAppComponent;
import ru.aydarov.randroid.presentation.di.app.NetworkModule;
import ru.aydarov.randroid.presentation.di.comments.CommentComponent;
import ru.aydarov.randroid.presentation.di.comments.InteractorModuleComment;
import ru.aydarov.randroid.presentation.di.comments.NetworkModuleComment;
import ru.aydarov.randroid.presentation.di.comments.RepositoryModuleComment;
import ru.aydarov.randroid.presentation.util.LiveConnectUtil;

/**
 * @author Aydarov Askhar 2020
 */
public class App extends Application {
    private static AppComponent sAppComponent;
    private static CommentComponent sCommentComponent;

    @Override
    public void onCreate() {
        LiveConnectUtil.getInstance().init(this);
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
        super.onCreate();
    }

    public static CommentComponent getCommentComponent() {
        if (sCommentComponent == null) {
            sCommentComponent = sAppComponent
                    .commentComponentBuilder()
                    .networkModule(new NetworkModuleComment())
                    .repositoryModule(new RepositoryModuleComment())
                    .interactorModule(new InteractorModuleComment())
                    .build();
        }
        return sCommentComponent;
    }

    public static void clearCommentComponet() {
        sCommentComponent = null;
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
