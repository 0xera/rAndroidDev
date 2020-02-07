package ru.aydarov.randroid.presentation.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import dagger.Lazy
import io.reactivex.disposables.Disposable
import ru.aydarov.randroid.data.model.UserData
import ru.aydarov.randroid.domain.user.UserInteractor
import javax.inject.Inject


/**
 * @author Aydarov Askhar 2020
 */
class UserViewModel(private val mInteractor: Lazy<UserInteractor>) : ViewModel() {
    private var mDisposable: Disposable? = null
    val user = MutableLiveData<UserData>()
    val result = MutableLiveData<Result>(Result.NONE)


    fun fetchMyData(accessToken: String) {
        mDisposable = mInteractor.get().getUserData(accessToken)
                .subscribe({
                    user.postValue(it)
                    result.postValue(Result.SUCCESS)
                }, {
                    result.postValue(Result.ERROR)
                })

    }

//    private fun getRetrofit(): Retrofit? {
//        if (mRetrofit == null) {
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.level = HttpLoggingInterceptor.Level.HEADERS
//            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//            mRetrofit = Retrofit.Builder()
//                    .baseUrl(RedditUtils.OAUTH_API_BASE_URI)
//                    .client(client)
//                    .addConverterFactory(GsonConverterFactory.create(Gson()))
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build()
//        }
//        return mRetrofit
//    }
//
//    private fun getApi(): RedditOauthAPI? {
//        if (mRedditAPI == null) mRedditAPI = getRetrofit()?.create(RedditOauthAPI::class.java)
//        return mRedditAPI
//    }

    fun dispose() {
        mInteractor.get().dispose()
    }

    override fun onCleared() {
        dispose()
        super.onCleared()
    }

    fun logOut() {
        mInteractor.get().logOut(user.value?.name)
    }

    enum class Result {
        NONE,
        SUCCESS,
        ERROR
    }


    class UserViewModelFactory @Inject constructor() : NewInstanceFactory() {
        @Inject
        lateinit var mInteractor: Lazy<UserInteractor>


        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(mInteractor) as T
        }
    }

}
