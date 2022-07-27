package com.szn.lbc.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.szn.lbc.dao.AppDatabase
import com.szn.lbc.datastore.DataStoreManager
import com.szn.lbc.datastore.LAST_UPDATE
import com.szn.lbc.extensions.isOnline
import com.szn.lbc.model.Album
import com.szn.lbc.network.APIService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AlbumRemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val apiService: APIService,
    private val dataStoreManager: DataStoreManager): RemoteMediator<Int, Album>() {

    private val albumDao = database.albumDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Album>): RemoteMediator.MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.

                    lastItem.id
                }
            }

            // Retrofit's Coroutine CallAdapter dispatches on a worker thread.
            val response = apiService.getAlbums().getOrNull()
            Log.w("Mediator", "refresh $loadKey $loadType")
            if(response != null)
                dataStoreManager.add(LAST_UPDATE, System.currentTimeMillis())

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    albumDao.clearAll()
                }

                // Insert new users into database, which invalidates the current PagingData,
                // allowing Paging to present the updates in the DB.
                response?.let { albumDao.insertAll(it) }
            }

            MediatorResult.Success(
                endOfPaginationReached = !response.isNullOrEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }


    /**
     * check whether cached data is out of date and decide whether to trigger a remote refresh.
     */
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(10, TimeUnit.MINUTES)
        val del = System.currentTimeMillis() - dataStoreManager.lastUpdated()
        val network = isOnline(dataStoreManager.context)
        return if ((del < cacheTimeout) || !network)
        {
            //Need to refresh cached data
            Log.e("Mediator", "No Need to refresh... $del < $cacheTimeout ")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.e("Mediator", "Need to refresh... $del > $cacheTimeout ")
            // Cached data is up-to-date, so there is no need to re-fetch
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
}