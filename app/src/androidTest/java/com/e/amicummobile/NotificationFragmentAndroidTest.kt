package com.e.amicummobile

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.e.amicummobile.view.notification.NotificationFragment
import com.e.amicummobile.view.notification.adapters.RvGroupNotificationAdapter
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)                                                       // порядок выполнения тестов
class NotificationFragmentAndroidTest {
    lateinit var scenario: ActivityScenario<MainActivity>

    //
    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            Thread.sleep(3000)
            it.supportFragmentManager.beginTransaction().replace(R.id.container, NotificationFragment(), "fragment-tag").commitAllowingStateLoss()
        }
    }

    @Test
    fun t1_activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }


    @Test
    fun t2_notificationTab_is_visible() {
        onView(withId(R.id.tabNotifications)).check(matches(isDisplayed()))
    }

    @Test
    fun t3_notificationTab_scroll() {
        onView(withId(R.id.rvGroupNotification))
            .perform(
                RecyclerViewActions.scrollToPosition<RvGroupNotificationAdapter.GroupNotificationHolder>(4)
            )
    }


    @After
    fun close() {
        scenario.close()
    }
}