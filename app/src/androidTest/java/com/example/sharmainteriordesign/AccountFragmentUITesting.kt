package com.example.sharmainteriordesign

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
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
class AccountFragmentUITesting {
    @get:Rule
    val testRule= ActivityScenarioRule(LoginActivity::class.java)
    @Test
    fun checkAccountAdddUI(){
        onView(withId(R.id.email))
                .perform(typeText("binod@gmail.com"))
        Espresso.closeSoftKeyboard()
//        Thread.sleep(100)
        onView(withId(R.id.password))
                .perform(typeText("binod"))

        Thread.sleep(2500)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.button))
                .perform(ViewActions.click())
        Thread.sleep(4000)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.ic_account))
                .perform(ViewActions.click())

        Thread.sleep(2500)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.add))
                .perform(ViewActions.click())
        Thread.sleep(2500)

        Espresso.onView(ViewMatchers.withId(R.id.profilepic))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}