package com.szn.lbc

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.szn.lbc.dao.AlbumDao
import com.szn.lbc.dao.AppDatabase
import com.szn.lbc.model.Album
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Test implementation for Database & DAO
 */
@RunWith(AndroidJUnit4::class)
class DbTest {

    private lateinit var db: AppDatabase
    private lateinit var albumDao: AlbumDao

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = AppDatabase.getDatabase(context)
        albumDao = db.albumDao()
    }

    @Test
    @Throws(Exception::class)
    fun writeAlbumAndRead(){
        val TEST = "Test"
        val album = Album(0, 0, TEST, TEST, TEST)
        // runBlocking for launch in a CoroutineScope
        runBlocking {
            albumDao.insert(album)

            val who = albumDao.getByTitle(TEST)
            assert(who[0].title == TEST)
            assert(who[0].title != "Toto")
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}