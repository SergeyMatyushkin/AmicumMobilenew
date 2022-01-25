package com.e.amicummobile.viewmodel

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.e.amicummobile.R
import com.e.amicummobile.view.notification.NotificationFragment
import com.e.amicummobile.view.startApplication.AuthorizationFragment
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import junit.framework.Assert.assertEquals

import junit.framework.Assert.assertNull
import org.junit.After

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito.mock
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(
    sdk = [30],
    instrumentedPackages = ["androidx.loader.content"]
)
class NotificationFragmentTest {
    lateinit var scenario: FragmentScenario<NotificationFragment>

    //
    @Before
    fun setup() {
        scenario = FragmentScenario.launch(NotificationFragment::class.java)
    }


    @Test
    fun `test tabNotifications grouptab`() {
        lateinit var tabNotifications: TabLayout

        scenario.onFragment {
            tabNotifications = it.getView()?.findViewById(R.id.tabNotifications)!!
        }

        tabNotifications.selectTab(tabNotifications.getTabAt(0))

        assertEquals(tabNotifications.selectedTabPosition, 0)

    }

    @Test
    fun `test tabNotifications personaltab`() {
        lateinit var tabNotifications: TabLayout

        scenario.onFragment {
            tabNotifications = it.getView()?.findViewById(R.id.tabNotifications)!!
        }
        tabNotifications.selectTab(tabNotifications.getTabAt(1))

        assertEquals(tabNotifications.selectedTabPosition, 1)

    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @After
    fun close() {
        scenario.close()
    }
}