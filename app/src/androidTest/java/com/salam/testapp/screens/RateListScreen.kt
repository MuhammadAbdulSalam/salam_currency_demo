package com.salam.testapp.screens

import com.salam.testapp.BaseTestRules
import com.salam.testapp.robots.onRateListScreen
import org.junit.Test

class RateListScreen: BaseTestRules() {

    @Test
    fun test_A_ClickFetchButton(){

        onRateListScreen {
            addAmount("20")
            onFetchButton()
            pauseTest()

            recyclerItemSelect(0)
            recyclerItemSelect(1)
            pauseTest()

            onHistoryButton()
            pauseTest()
        }
    }
}