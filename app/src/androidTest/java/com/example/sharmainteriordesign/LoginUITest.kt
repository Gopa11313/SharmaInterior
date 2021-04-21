package com.example.sharmainteriordesign

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.sharmainteriordesign.ui.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class LoginUITest {
    @get:Rule
    val testRule=ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkLoginUI(){
        onView(withId(R.id.email))
                .perform(typeText("binod@gmail.com"))
        closeSoftKeyboard()
        Thread.sleep(1000)
        onView(withId(R.id.password))
                .perform(typeText("binod"))

        Thread.sleep(1000)
        closeSoftKeyboard()
        onView(withId(R.id.button))
                .perform(click())
        Thread.sleep(10000)
        onView(withId(R.id.fragmentsHolder))
                .check(matches(isDisplayed()))
    }
}