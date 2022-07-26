package com.szn.lbc

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.PerformException
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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

    private val DELAY = 2000L

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
        Espresso.onView(ViewMatchers.withId(R.id.recycler)) // scrollTo will fail the test if no item matches.
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("not in the list"))
                )
            )
    }

    @Test
    fun scrollToTwenty(){
        Espresso.onView(ViewMatchers.withId(R.id.recycler))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20)
            )
    }

    /**
     * With the Pager implementation, should not fail with huge value
     */
    @Test
    fun scrollToHugeThenValThenTop(){
        runBlocking {
            Espresso.onView(ViewMatchers.withId(R.id.recycler))
                .perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(Int.MAX_VALUE)
                )

            delay(DELAY)
            Espresso.onView(ViewMatchers.withId(R.id.recycler))
                .perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(100)
                )

            delay(DELAY)
            Espresso.onView(ViewMatchers.withId(R.id.recycler))
                .perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
                )
            delay(DELAY)
        }

    }
}