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
    var user: MutableLiveData<UserData>? = MutableLiveData()
    var result = MutableLiveData<Result>(Result.NONE)


    fun fetchMyData(accessToken: String) {
        mDisposable = mInteractor.get().getUserData(accessToken)
                .subscribe({
                    user?.postValue(it)
                    result.postValue(Result.SUCCESS)
                }, {
                    result.postValue(Result.ERROR)
                })

    }


    fun dispose() {
        mInteractor.get().dispose()
    }

    override fun onCleared() {
        dispose()
        super.onCleared()
    }

    fun logOut() {
        mInteractor.get().logOut(user?.value?.name)
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
