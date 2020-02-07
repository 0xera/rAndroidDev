package ru.aydarov.randroid.presentation.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.aydarov.randroid.presentation.ui.user.UserFragment;
import ru.aydarov.randroid.presentation.ui.web.WebViewActivity;

/**
 * @author Aydarov Askhar 2020
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, InteractorModule.class, RepositoryModule.class})
public interface AppComponent {

    void inject(UserFragment fragment);

    void inject(WebViewActivity activity);
}
