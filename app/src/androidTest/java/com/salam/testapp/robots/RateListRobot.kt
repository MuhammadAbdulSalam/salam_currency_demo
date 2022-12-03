package com.salam.testapp.robots

import com.salam.testapp.R
import com.salam.testapp.robots.baserobot.BaseRobot


fun onRateListScreen(func: RateListRobot.() -> Unit) = RateListRobot().apply { func() }

class RateListRobot: BaseRobot() {

    fun onFetchClick(){
        clickButton(R.id.btn_fetch, "fetch")
    }

    fun addAmount(amount: String){
        sentText(R.id.tv_amount, amount)
    }

    fun recyclerItemSelect(position: Int){
        longPress(position)
    }

    fun onHistoryClicked(){
        clickButton(R.id.btn_history, "History")
    }

}