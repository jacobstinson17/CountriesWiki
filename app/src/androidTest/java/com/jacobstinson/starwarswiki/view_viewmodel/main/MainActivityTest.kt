package com.jacobstinson.starwarswiki.view_viewmodel.main

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.jacobstinson.starwarswiki.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testNavHostFragmentShown() {
        Espresso.onView(ViewMatchers.withId(R.id.nav_host_fragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}