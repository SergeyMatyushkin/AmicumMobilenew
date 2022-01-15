package com.e.amicummobile.viewmodel

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import junit.framework.Assert.assertEquals
import org.junit.After

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.e.amicummobile.MainActivity
import com.e.amicummobile.R
import com.e.amicummobile.view.startApplication.AuthorizationFragment
import com.google.android.material.textfield.TextInputEditText

import junit.framework.Assert.assertNull
import org.koin.core.context.stopKoin
import org.mockito.Mockito.mock

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(
    sdk = [30],
    instrumentedPackages = ["androidx.loader.content"]
)
class AuthorizationFragmentTest {
    lateinit var scenario: FragmentScenario<AuthorizationFragment>

    @Before
    fun setup() {
        scenario = FragmentScenario.launch(AuthorizationFragment::class.java)
    }

    @Test
    fun `test empty text in login field`() {
        lateinit var txtLogin: TextInputEditText
        scenario.onFragment {
            txtLogin = it.getView()?.findViewById(R.id.txtLogin)!!
        }

        assertEquals(txtLogin.text.toString(), "")

    }

    @Test
    fun `test empty text in pwd field`() {
        lateinit var txtPwd: TextInputEditText

        scenario.onFragment {
            txtPwd = it.getView()?.findViewById(R.id.txtPwd)!!
        }
        assertEquals(txtPwd.text.toString(), "")

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