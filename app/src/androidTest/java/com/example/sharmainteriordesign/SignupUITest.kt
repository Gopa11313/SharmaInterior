package com.example.sharmainteriordesign
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.sharmainteriordesign.ui.SignUpActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class SignupUITest {
    @get:Rule
    val testRule=ActivityScenarioRule(SignUpActivity::class.java)

    @Test
    fun checkSignuPUI(){
        Espresso.onView(ViewMatchers.withId(R.id.name))
                .perform(ViewActions.typeText("UI testing"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.address))
                .perform(ViewActions.typeText("UITestning"))
        Thread.sleep(500)
        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("UITestning@gmail.com"))
        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("UITestning"))
        Thread.sleep(500)
//        Espresso.onView(ViewMatchers.withId(R.id.con_password))
//                .perform(typeText("UITestning"))
//        Thread.sleep(500)
        Espresso.closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.signup))
                .perform(click())
        Thread.sleep(10000)
        onView(ViewMatchers.withId(R.id.name))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}