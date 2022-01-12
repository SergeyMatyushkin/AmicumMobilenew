package com.e.amicummobile.viewmodel

import android.os.Build
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Lifecycle

import androidx.test.core.app.ActivityScenario

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.e.amicummobile.MainActivity
import com.e.amicummobile.R
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityContainer_NotNull() {
        scenario.onActivity {
            val totalCountTextView =
                it.findViewById<FragmentContainerView>(R.id.container)
            assertNotNull(totalCountTextView)
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}