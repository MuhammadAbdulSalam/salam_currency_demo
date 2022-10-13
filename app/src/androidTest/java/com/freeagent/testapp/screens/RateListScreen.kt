package com.freeagent.testapp.screens

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.freeagent.testapp.activites.mainactivity.MainActivity
import com.freeagent.testapp.robots.onRateListScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RateListScreen {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        Thread.sleep(1000)
    }

    @Test
    fun clickFetchButton(){
        onRateListScreen {
            addAmount("10")
            onFetchClick()
            Thread.sleep(10000)
            recyclerLongPress(0)
            recyclerLongPress(1)
            onHistoryClicked()
        }
    }
}