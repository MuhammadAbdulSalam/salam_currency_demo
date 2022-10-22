package com.freeagent.testapp.robots.baserobot


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.freeagent.testapp.R
import org.hamcrest.Matchers


open class BaseRobot {

    fun clickButton(id: Int, btnTitle: String): ViewInteraction = onView(
        Matchers.allOf(
            ViewMatchers.withId(id),
            ViewMatchers.withText(btnTitle),
            ViewMatchers.isDisplayed()
        )
    ).perform(ViewActions.click())

    fun sentText(id: Int, text: String): ViewInteraction = onView(
        Matchers.allOf(
            ViewMatchers.withId(id),
            ViewMatchers.isDisplayed()
        )
    ).perform(ViewActions.typeText(text))

    fun longPress(position: Int){
        onView(withId(R.id.rates_list_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, ViewActions.click())
        )

    }
}