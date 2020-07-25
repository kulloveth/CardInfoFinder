package com.kulloveth.cardinfofinder.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kulloveth.cardinfofinder.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val validCardNo = 539976458452
    private val scheme = "mastercard"
    private val country = "Canada"


    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(MainActivity::class.java)
    }






    @Test
    fun checkThatCountryISDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.card_no_input))
            .perform(typeText(validCardNo.toString()))
        onView(withId(R.id.country)).check(matches(withText(country)))
    }

    @Test
    fun checkThatSchemeTypeISDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.card_no_input))
            .perform(typeText(validCardNo.toString()))
        onView(withId(R.id.scheme_type)).check(matches(withText(scheme)))
    }




}