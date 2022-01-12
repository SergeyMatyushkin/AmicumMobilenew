package com.e.amicummobile.viewmodel

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.e.amicummobile.MainActivity
import com.e.amicummobile.R
import com.e.amicummobile.view.startApplication.AuthorizationFragment
import com.google.android.material.textfield.TextInputEditText

import junit.framework.Assert.assertNull

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [30])
class AuthorizationFragmentTest {
    lateinit var scenarioActivity: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenarioActivity = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun `test empty text in login field`() {
        val scenario = launchFragmentInContainer<AuthorizationFragment>()
        lateinit var txtLogin: TextInputEditText
        scenarioActivity.onActivity {
            txtLogin = it.findViewById(R.id.txtLogin)
        }
        assertNull(txtLogin.text)
    }
}