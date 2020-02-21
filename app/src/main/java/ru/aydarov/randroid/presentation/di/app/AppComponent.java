package ru.aydarov.randroid.presentation.di.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.aydarov.randroid.presentation.di.comments.CommentComponent;
import ru.aydarov.randroid.presentation.ui.post.PostListFragment;
import ru.aydarov.randroid.presentation.ui.searched.SearchedFragment;
import ru.aydarov.randroid.presentation.ui.user.UserFragment;
import ru.aydarov.randroid.presentation.ui.web.WebViewActivity;

/**
 * @author Aydarov Askhar 2020
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, InteractorModule.class, RepositoryModule.class})
public interface AppComponent {
    CommentComponent.Builder commentComponentBuilder();

    void inject(UserFragment fragment);

    void inject(SearchedFragment fragment);

    void inject(PostListFragment fragment);

    void inject(WebViewActivity activity);
}
