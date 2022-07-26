package com.szn.lbc.repo

import android.util.Log
import com.szn.lbc.dao.AlbumDao
import com.szn.lbc.model.Album
import com.szn.lbc.network.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepository @Inject constructor(private val apiService: APIService,
                                           private val albumDao: AlbumDao) {

    val albums = mutableListOf<Album>()

    init {

    }

    private suspend fun loadAlbums(){
        apiService.getAlbums()
            .onFailure {
                Log.e(TAG, "Error while get Albums")
            }
            .onSuccess {
                Log.w(TAG, "Success while get Albums ${it.size} ${it[0].title}")
                albumDao.insertAll(it)
                albums.addAll(it)
            }
    }

    suspend fun checkDb() {
        CoroutineScope(Dispatchers.IO).launch {
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