package ru.aydarov.randroid.presentation.ui.post;

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
import ru.aydarov.randroid.data.repository.api.post.RedditPostAPI;
import ru.aydarov.randroid.data.repository.repo.post.RepositoryPostImpl;
import ru.aydarov.randroid.domain.post.PostInteractor;
import ru.aydarov.randroid.domain.post.PostInteractorImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.aydarov.randroid.domain.post.PostInteractorImpl.PAGE_SIZE;

public class PostViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    PostViewModel mPostViewModel;

    @Mock
    RedditPostAPI mRedditPostAPI;
    @Mock
    Lazy<RedditPostAPI> mRedditPostAPILazy;

    @Mock
    PostInteractorImpl mPostInteractor;

    @Mock
    RepositoryPostImpl mRepositoryPost;
    @Mock
    Lazy<RepositoryPostImpl> mRepositoryPostLazy;
    @Mock
    Lazy<PostInteractor> mPostInteractorLazy;


    @Spy
    MutableLiveData<String> mSortType;
    @Mock
    ListingPost<RedditPost> mPosts;
    @Mock
    Observer<String> observer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPostViewModel = new PostViewModel(null, mPostInteractorLazy);
        mPostViewModel.setSortTypeLive(mSortType);
        when(mPostInteractorLazy.get()).thenReturn(mPostInteractor);
        when(mRepositoryPostLazy.get()).thenReturn(mRepositoryPost);
        when(mRedditPostAPILazy.get()).thenReturn(mRedditPostAPI);
        when(mPostInteractor.getPosts("hot", PAGE_SIZE)).thenReturn(mPosts);


    }

    @Test
    public void updateSortType() {
        mPostViewModel.updateSortType("hot");
        mPostViewModel.getSortTypeLive().observeForever(observer);
        verify(observer).onChanged("hot");
    }

}