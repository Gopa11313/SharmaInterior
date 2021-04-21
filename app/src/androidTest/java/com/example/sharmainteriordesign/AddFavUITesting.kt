package com.example.sharmainteriordesign

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.sharmainteriordesign.ui.AddProductActivity
import com.example.sharmainteriordesign.ui.LoginActivity
import org.junit.Rule
import org.junit.Test

class AddFavUITesting {
    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkAddFAvUI() {
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("binod@gmail.com"))
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("binod"))

        Thread.sleep(1000)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.button))
                .perform(ViewActions.click())
        Thread.sleep(4000)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.ic_fav))
                .perform(ViewActions.click())
    Espresso.onView(ViewMatchers.withId(R.id.favRecycle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}
