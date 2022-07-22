package com.szn.lbc.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.szn.lbc.model.Album
import com.szn.lbc.network.APIService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepository @Inject constructor(private val apiService: APIService) {

    val albums = MutableLiveData<List<Album>>()

    init {

    }

    suspend fun loadAlbums(){
        apiService.getAlbums()
            .onFailure {
                Log.e(TAG, "Error while get Albums")
            }
            .onSuccess {
                Log.w(TAG, "Success while get Albums ${it.size} ${it[0].title}")
                albums.value = it
            }
    }


    companion object {
        val TAG = AlbumsRepository::class.java.simpleName
    }

}