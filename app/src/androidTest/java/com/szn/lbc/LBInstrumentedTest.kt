package com.szn.lbc

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.szn.lbc.ui.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LBInstrumentedTest {

    private val DELAY = 1000L

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals(BuildConfig.APPLICATION_ID, appContext.packageName)
    }

    @Test(expected = PerformException::class)
    fun itemWithText_doesNotExist() {
        // Attempt to scroll to an item that contains the special text.
        onView(ViewMatchers.withId(R.id.recycler)) // scrollTo will fail the test if no item matches.
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("not in the list"))
                )
            )
    }

    /**
     * With the Pager implementation, should not fail with huge value
     */
    @Test
    fun scrollToHugeThenValThenTop(){
        runBlocking {
            onView(ViewMatchers.withId(R.id.recycler))
                .perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(Int.MAX_VALUE)
                )

            delay(DELAY)
            onView(ViewMatchers.withId(R.id.recycler))
                .perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(100)
                )

            delay(DELAY)
            onView(ViewMatchers.withId(R.id.recycler))
                .perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
                )
            delay(DELAY)
        }

    }

    @Test
    fun scrollToBottom() {
        runBlocking{
            delay(DELAY)
            onView(ViewMatchers.withId(R.id.recycler)).check { view, noViewFoundException ->
                val recyclerView = view as RecyclerView
                val count = recyclerView.adapter?.itemCount!!
                assertTrue(count > 0)
                recyclerView.scrollToPosition(count - 1)
            }
            delay(DELAY * 2)
        }
    }

    @Test
    fun scrollAnScroll() {
        runBlocking{
            delay(DELAY)
            val v = onView(ViewMatchers.withId(R.id.recycler))
            v.check { view, noViewFoundException ->
                val recyclerView = view as RecyclerView
                val count = recyclerView.adapter?.itemCount!!
                assertTrue(count > 0)
                recyclerView.scrollToPosition(count - 1)
            }
            delay(DELAY * 2)
            v.check{ view, noViewFoundException ->
                val recyclerView = view as RecyclerView
                val count = recyclerView.adapter?.itemCount!!
                assertTrue(count > 1)
                recyclerView.scrollToPosition(count-1)
                recyclerView.scrollToPosition(1)
                recyclerView.scrollToPosition(count)
                recyclerView.scrollToPosition(count / 2)
            }
        }
    }


    @Test
    fun loadsTheDefaultResults() {
        onView(ViewMatchers.withId(R.id.recycler)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertTrue(recyclerView.adapter?.itemCount!! > 1)
        }
    }


    @Test
    fun loadsTheTestResultsMax() {

        onView(ViewMatchers.withId(R.id.recycler)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertTrue(recyclerView.adapter?.itemCount!! > 1)
            assertTrue(recyclerView.adapter?.itemCount!! == 5000)
        }
    }

    @Test
    fun loadDetail(){
        runBlocking {
            onView(ViewMatchers.withId(R.id.recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
            delay(DELAY)
            onView(ViewMatchers.withId(R.id.img_iv))
                .check{ view, noViewFoundException ->
                    assert(view != null)
                }
        }

    }

}