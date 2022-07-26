package com.szn.lbc

import android.app.Application
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PagerAdapterTest {

    @Before
    fun init() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun loadsTheDefaultResults() {
        onView(withId(R.id.recycler)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertTrue(recyclerView.adapter?.itemCount!! > 1)
        }
    }


    @Test
    fun loadsTheTestResultsMax() {

        onView(withId(R.id.recycler)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertTrue(recyclerView.adapter?.itemCount!! > 1)
            assertTrue(recyclerView.adapter?.itemCount!! == 5000)
        }
    }

}