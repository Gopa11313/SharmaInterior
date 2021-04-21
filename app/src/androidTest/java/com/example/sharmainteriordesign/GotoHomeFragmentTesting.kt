package com.example.sharmainteriordesign

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.sharmainteriordesign.ui.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class GotoHomeFragmentTesting {
    @get:Rule
    val testRule= ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkHomeRecylarUI(){
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("binod@gmail.com"))
        Espresso.closeSoftKeyboard()
//        Thread.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("binod"))

        Thread.sleep(100)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.button))
                .perform(ViewActions.click())

        Thread.sleep(2500)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.ic_home))
                .perform(ViewActions.click())
        Thread.sleep(2500)
        Espresso.onView(ViewMatchers.withId(R.id.recyclar))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))



    }
}