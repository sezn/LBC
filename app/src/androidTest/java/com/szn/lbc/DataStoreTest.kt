package com.szn.lbc

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.szn.lbc.datastore.DataStoreManager
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * DataStore Test implementation
 */
@RunWith(AndroidJUnit4::class)
class DataStoreTest {

    private lateinit var dataStoreManager: DataStoreManager
    val TEST = "Test"

    @Before
    fun createDataStore(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataStoreManager = DataStoreManager(context)
    }

    @Test
    fun writeToStore(){
        runBlocking {
            dataStoreManager.add(TEST, TEST)
        }
    }
    @Test
    fun readFromStore(){
        runBlocking {
            val test = dataStoreManager.get(TEST)
            assert(test != null)
            assert(test == TEST)
        }
    }

}