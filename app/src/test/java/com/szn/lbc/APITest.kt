package com.szn.lbc

import com.szn.lbc.network.APIService
import com.szn.lbc.network.ResultCallAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APITest {

    private lateinit var service: APIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(BuildConfig.BASE_URL))
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
        server.shutdown()
    }
}