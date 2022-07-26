package com.szn.lbc.repo

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.szn.lbc.dao.AlbumDao
import com.szn.lbc.dao.AppDatabase
import com.szn.lbc.datastore.DataStoreManager
import com.szn.lbc.datastore.LAST_UPDATE
import com.szn.lbc.model.Album
import com.szn.lbc.network.APIService
import com.szn.lbc.paging.AlbumRemoteMediator
import com.szn.lbc.paging.AlbumsPagingDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepository @Inject constructor(private val apiService: APIService,
                                           private val database: AppDatabase,
                                           private val dataStoreManager: DataStoreManager) {

    val albumDao = database.albumDao()
    val albums = mutableListOf<Album>()

    @OptIn(ExperimentalPagingApi::class)
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20),
        remoteMediator = AlbumRemoteMediator(database, apiService, dataStoreManager)
    ) {
//        AlbumsPagingDataSource(this)
        albumDao.pagingSource()
    }.flow

    init {
        CoroutineScope(Dispatchers.Main).launch {
            val last = dataStoreManager.getValue(LAST_UPDATE)
            if(last != null){
                val dt = Date(last.toString().toLong())
                Log.w(TAG, "init $last   $dt")
            }
        }
    }

    private suspend fun loadAlbums(){
        apiService.getAlbums()
            .onFailure {
                Log.e(TAG, "Error while get Albums")
            }
            .onSuccess {
                Log.w(TAG, "Success while get Albums ${it.size}")
                albumDao.insertAll(it)
                albums.addAll(it)
                dataStoreManager.add(LAST_UPDATE, System.currentTimeMillis())
            }
    }

    suspend fun checkDb() {
        CoroutineScope(Dispatchers.IO).launch{
            val abs = albumDao.getAll()
            if(!abs.isNullOrEmpty()){
                albums.clear()
                albums.addAll(abs)
            } else {
                loadAlbums()
            }
        }
    }

    companion object {
        val TAG = AlbumsRepository::class.java.simpleName
    }

}