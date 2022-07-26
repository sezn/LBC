package com.szn.lbc

import com.szn.lbc.network.APIService
import com.szn.lbc.network.ResultCallAdapterFactory
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APITest {

    private lateinit var service: APIService

    @Before
    fun setUp() {
        service = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
            .create(APIService::class.java)
    }

    @Test
    fun testAlbumsLoaded(){
        runBlocking {
            val albums = service.getAlbums().getOrThrow()
            assert(albums.isNotEmpty())
            assert(albums.size > 10)
        }
    }

    @After
    fun tearDown() {
    }
}