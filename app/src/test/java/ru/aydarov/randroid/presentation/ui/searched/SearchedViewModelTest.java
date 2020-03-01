package ru.aydarov.randroid.presentation.ui.searched;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import dagger.Lazy;
import ru.aydarov.randroid.data.model.ListingPost;
import ru.aydarov.randroid.data.model.RedditPost;
import ru.aydarov.randroid.data.repository.api.search.RedditSearchApi;
import ru.aydarov.randroid.data.repository.repo.search.RepositorySearch;
import ru.aydarov.randroid.data.repository.repo.search.RepositorySearchImpl;
import ru.aydarov.randroid.domain.search.SearchInteractor;
import ru.aydarov.randroid.domain.search.SearchInteractorImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.aydarov.randroid.domain.post.PostInteractorImpl.PAGE_SIZE;

public class SearchedViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    SearchedViewModel mSearchedViewModel;

    @Mock
    RedditSearchApi mRedditSearchApi;
    @Mock
    Lazy<RedditSearchApi> mRedditSearchApiLazy;

    @Mock
    SearchInteractorImpl mSearchInteractor;

    @Mock
    RepositorySearchImpl mRepositoryPost;
    @Mock
    Lazy<RepositorySearch> mRepositorySearchLazy;
    @Mock
    Lazy<SearchInteractor> mSearchInteractorLazy;


    @Spy
    MutableLiveData<String> mSortType;
    @Mock
    ListingPost<RedditPost> mPosts;
    @Mock
    Observer<String> observer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mSearchedViewModel = new SearchedViewModel(null, mSearchInteractorLazy);
        mSearchedViewModel.setSortTypeLive(mSortType);
        when(mSearchInteractorLazy.get()).thenReturn(mSearchInteractor);
        when(mRepositorySearchLazy.get()).thenReturn(mRepositoryPost);
        when(mRedditSearchApiLazy.get()).thenReturn(mRedditSearchApi);
        when(mSearchInteractor.getPosts("hot", PAGE_SIZE, "query")).thenReturn(mPosts);


    }

    @Test
    public void updateSortType() {
        mSearchedViewModel.updateSortType("hot:query");
        mSearchedViewModel.getSortTypeLive().observeForever(observer);
        verify(observer).onChanged("hot:query");
    }

}