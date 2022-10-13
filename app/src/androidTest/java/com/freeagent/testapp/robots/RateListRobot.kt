package com.freeagent.testapp.robots

import com.freeagent.testapp.R
import com.freeagent.testapp.robots.baserobot.BaseRobot


fun onRateListScreen(func: RateListRobot.() -> Unit) = RateListRobot().apply { func }

class RateListRobot: BaseRobot() {

    fun onFetchClick(){
        clickButton(R.id.btn_fetch, "fetch")
    }

    fun addAmount(amount: String){
        sentText(R.id.tv_amount, amount)
    }

    fun recyclerLongPress(position: Int){
        longPress(position)
    }

    fun onHistoryClicked(){
        clickButton(R.id.btn_history, "history")
    }

}