package com.szn.lbc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

@HiltAndroidApp
class LBCApp: Application() {


    override fun onCreate() {
        super.onCreate()
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        CookieHandler.setDefault(cookieManager)
    }
}