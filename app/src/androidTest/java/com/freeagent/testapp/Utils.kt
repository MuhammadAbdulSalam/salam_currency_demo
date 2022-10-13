package com.freeagent.testapp

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher
import androidx.test.espresso.action.ViewActions


class ClickOnRecyclerItem : ViewAction {
    var click: ViewAction = ViewActions.longClick()
    override fun getConstraints(): Matcher<View> {
        return click.constraints
    }

    override fun getDescription(): String {
        return " click on custom image view"
    }

    override fun perform(uiController: UiController, view: View) {
        click.perform(uiController, view.findViewById(R.id.layout_parent_view))
    }
}