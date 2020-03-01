package ru.aydarov.randroid.presentation.ui.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import dagger.Lazy
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import ru.aydarov.randroid.data.model.UserData
import ru.aydarov.randroid.domain.user.UserInteractor
import ru.aydarov.randroid.domain.user.UserInteractorImpl

class UserViewModelTest {


    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    var userViewModel: UserViewModel? = null

    @Mock
    var userInteractor: UserInteractorImpl? = null

    @Mock
    var interactorLazy: Lazy<UserInteractor>? = null

    @Spy
    var result: MutableLiveData<UserViewModel.Result>? = null
    @Spy
    var user: MutableLiveData<UserData>? = null
    @Mock
    var observerResult: Observer<UserViewModel.Result>? = null
    @Mock
    var observerUser: Observer<UserData>? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userViewModel = interactorLazy?.let { UserViewModel(it) }
        userViewModel?.user = user
        userViewModel?.result = result!!
        observerUser?.let { userViewModel?.user?.observeForever(it) }
        observerResult?.let { userViewModel?.result?.observeForever(it) }
        Mockito.`when`<UserInteractor>(interactorLazy?.get()).thenReturn(userInteractor)


    }

    @Test
    fun fetchMyDataSuccess() {
        val userData = UserData("name", "icon", UserData.UserSubReddit("banner", "description"))
        Mockito.`when`<Flowable<UserData>>(userInteractor?.getUserData("123")).thenReturn(Flowable.just(userData))
        userViewModel?.fetchMyData("123")
        Mockito.verify(observerUser)?.onChanged(userData)
        Mockito.verify(observerResult)?.onChanged(UserViewModel.Result.SUCCESS)
    }
    @Test
    fun fetchMyDataError() {
        Mockito.`when`<Flowable<UserData>>(userInteractor?.getUserData("123")).thenReturn(Flowable.error(Throwable("it_me")))
        userViewModel?.fetchMyData("123")
        Mockito.verify(observerResult)?.onChanged(UserViewModel.Result.ERROR)
    }

    @After
    fun afterTest() {
        observerUser?.let { userViewModel?.user?.removeObserver(it) }
        observerResult?.let { userViewModel?.result?.removeObserver(it) }
    }

}