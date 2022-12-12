package com.salam.testapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.salam.testapp.activites.mainactivity.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
open class BaseTestRules {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        pauseTest()
    }

    fun pauseTest(){
        Thread.sleep(3000) //wait for app load
    }
}