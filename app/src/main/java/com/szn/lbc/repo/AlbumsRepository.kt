package com.szn.lbc.repo

import android.util.Log
import com.szn.lbc.network.APIService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepository @Inject constructor(private val apiService: APIService) {

    init {

    }

    suspend fun loadAlbums(){
        apiService.getAlbums()
            .onFailure {
                Log.e(TAG, "Error while get Albums")
            }
            .onSuccess {
                Log.w(TAG, "Success while get Albums ${it.size} ${it.get(0).title}")

            }
    }


    companion object {
        val TAG = AlbumsRepository::class.java.simpleName
    }

}