package com.e.amicummobile

import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.e.amicummobile.view.startApplication.AuthorizationFragment
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthorizationFragmentAndroidTest {
    lateinit var scenario: ActivityScenario<MainActivity>

    //
    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(R.id.container, AuthorizationFragment(), "fragment-tag").commitAllowingStateLoss()
        }
        Thread.sleep(500)
    }

    @Test
    fun activityLblLogin_IsDisplayed() {
        onView(withId(R.id.layoutLogin)).check(matches(isDisplayed()))
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun checkBox_is_not_check() {
        onView(withId(R.id.checkBox)).check(matches(isNotChecked()))
    }

    @Test
    fun checkBox_is_check() {
        onView(withId(R.id.checkBox)).perform(click())
        onView(withId(R.id.checkBox)).check(matches(isChecked()))
    }

    @Test
    fun activityLogin_IsWorking() {
        onView(withId(R.id.txtLogin)).perform(click())
        onView(withId(R.id.txtLogin)).perform(
            replaceText(LOGIN),
            closeSoftKeyboard()
        )
        onView(withId(R.id.txtPwd)).perform(click())
        onView(withId(R.id.txtPwd)).perform(
            replaceText(PWD),
            closeSoftKeyboard()
        )

        onView(withId(R.id.txtPwd)).perform(pressImeActionButton())
        onView(withId(R.id.txtPwd)).check(matches(withText(PWD)))
    }

    @After
    fun close() {
        scenario.close()
    }
}