package com.jacobstinson.countrieswiki.view_viewmodel.splash

import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.jacobstinson.countrieswiki.view_viewmodel.main.MainActivity
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule<SplashActivity>(SplashActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @Test
    fun testMainActivityStartedAndSplashActivityFinished() {
        activityTestRule.launchActivity(Intent())

        intended(hasComponent(MainActivity::class.java.name))
        assertTrue(activityTestRule.activity.isDestroyed)
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}