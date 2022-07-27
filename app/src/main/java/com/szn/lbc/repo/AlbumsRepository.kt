package com.szn.lbc.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.szn.lbc.dao.AppDatabase
import com.szn.lbc.datastore.DataStoreManager
import com.szn.lbc.network.APIService
import com.szn.lbc.paging.AlbumRemoteMediator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepository @Inject constructor(
    apiService: APIService,
    database: AppDatabase,
    dataStoreManager: DataStoreManager) {

    private val albumDao = database.albumDao()

    /**
     * Reactive Stream of PagingData
     */
    @OptIn(ExperimentalPagingApi::class)
    val flow = Pager(
        PagingConfig(pageSize = 20),
        remoteMediator = AlbumRemoteMediator(database, apiService, dataStoreManager)
    ) {
        albumDao.pagingSource()
    }.flow
}