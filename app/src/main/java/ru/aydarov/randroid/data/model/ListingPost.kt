package ru.aydarov.randroid.data.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ru.aydarov.randroid.data.repository.repo.post.NetworkState

/**
 * @author Aydarov Askhar 2020
 */

data class ListingPost<T>(
        val pagedList: LiveData<PagedList<T>>,
        val networkState: LiveData<NetworkState>,
        val refreshState: LiveData<NetworkState>,
        val refresh: () -> Unit,
        val retry: () -> Unit)