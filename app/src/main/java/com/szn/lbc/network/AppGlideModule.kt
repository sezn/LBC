package com.szn.lbc.network

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import java.io.InputStream
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy


@GlideModule
class AppGlideModule: AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        CookieHandler.setDefault(cookieManager)

        val okHttpClient = OkHttpClient.Builder().apply {
            cookieJar(JavaNetCookieJar(cookieManager))
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    // Dont know why it s not working..
//                    builder.header("User-Agent", System.getProperty("http.agent"))
                    builder.header("User-Agent", "chelou")
                    builder.header("Cache-Control", "no-cache")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()

        val factory: OkHttpUrlLoader.Factory = OkHttpUrlLoader.Factory(okHttpClient)
        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }
}
