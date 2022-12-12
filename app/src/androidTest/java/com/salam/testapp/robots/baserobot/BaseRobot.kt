package com.salam.testapp.robots.baserobot


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.salam.testapp.R
import org.hamcrest.Matchers


open class BaseRobot {

    /**
     * Click on a button of choice
     *
     * @param id this is the ID of button that needs testing
     * @param btnTitle this is the title/text shown on button such as Home - Shop etc
     * @param performClick should CLICK BUTTON as true or NO as false
     *
     * This function includes tests for Match with following
     * ID should be as given
     * Title should be as given
     * Button should be visible on screen
     */
    fun clickButton(id: Int, btnTitle: String, performClick: Boolean){
        val view = onView(
            Matchers.allOf(
                withId(id),
                withText(btnTitle),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))
        if(performClick){
            view.perform(ViewActions.click())
        }
    }

    /**
     * Type text in TextFields
     *
     * @param id this is the ID of TextField
     * @param text Text to be typed in TextField
     *
     * This function includes tests for Match with following
     * ID should be as given
     * TextField should be Displayed on screen
     */
    fun sentText(id: Int, text: String): ViewInteraction = onView(
        Matchers.allOf(
            withId(id),
            isDisplayed()
        )
    ).perform(ViewActions.typeText(text))

    /**
     * Validate if a text on screen is displayed
     *
     * @param text Text that needs to be validated
     */
    fun isTextDisplayed(text: String) : ViewInteraction? = onView(
        Matchers.allOf(
            withText(text),
            isDisplayed()
        )
    ).check(matches(isDisplayed()))

    /**
     * This function performs click on requested item in recycler
     *
     * @param position position of item in recyclerview as int such as 1,2,3 etc
     */
    fun longPress(position: Int){
        onView(withId(R.id.rates_list_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, ViewActions.click())
        )

    }
}