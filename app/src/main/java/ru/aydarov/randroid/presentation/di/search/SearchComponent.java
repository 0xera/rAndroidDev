package ru.aydarov.randroid.presentation.di.search;

import dagger.Subcomponent;
import ru.aydarov.randroid.presentation.ui.searched.SearchedFragment;

@SearchScope
@Subcomponent(modules = {AppSearchModule.class, NetworkSearchModule.class, RepositorySearchModule.class, InteractorSearchModule.class})
public interface SearchComponent {

    @Subcomponent.Builder
    interface Builder {
        SearchComponent.Builder appModule(AppSearchModule module);

        SearchComponent.Builder networkModule(NetworkSearchModule module);

        SearchComponent.Builder repositoryModule(RepositorySearchModule module);

        SearchComponent.Builder interactorModule(InteractorSearchModule module);

        SearchComponent build();
    }

    void inject(SearchedFragment fragment);

}

