package ru.aydarov.randroid.data.model

import androidx.lifecycle.LiveData
import ru.aydarov.randroid.data.repository.repo.post.NetworkState

/**
 * @author Aydarov Askhar 2020
 */
data class ListingComment<T>(
        val commentList: LiveData<List<T>>,
        val networkState: LiveData<NetworkState>,
        val refreshState: LiveData<NetworkState>,
        val refresh: () -> Unit)