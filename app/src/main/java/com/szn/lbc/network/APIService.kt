package com.szn.lbc.network

import com.szn.lbc.model.Album
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): Response<List<Album>>

}