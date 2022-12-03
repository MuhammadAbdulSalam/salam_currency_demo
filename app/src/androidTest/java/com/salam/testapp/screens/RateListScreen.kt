package com.salam.testapp.screens

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.salam.testapp.activites.mainactivity.MainActivity
import com.salam.testapp.robots.onRateListScreen
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
        Thread.sleep(5000) //wait for app load
    }

    @Test
    fun clickFetchButton(){

        onRateListScreen {
            addAmount("20")
            onFetchClick()
            Thread.sleep(2000) //wait api response

            recyclerItemSelect(0)
            recyclerItemSelect(1)
            Thread.sleep(3000) //let ui update after selection

            onHistoryClicked()
            Thread.sleep(5000)
        }
    }
}