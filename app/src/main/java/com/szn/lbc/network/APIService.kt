package com.szn.lbc.network

import com.szn.lbc.model.Album
import retrofit2.http.GET

/**
 * Define Endpoints
 */
interface APIService {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): List<Album>

}