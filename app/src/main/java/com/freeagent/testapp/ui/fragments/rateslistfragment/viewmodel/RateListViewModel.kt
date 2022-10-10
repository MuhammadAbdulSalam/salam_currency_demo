package com.freeagent.testapp.ui.fragments.rateslistfragment.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.freeagent.testapp.api.apihelper.ApiHelper
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

class RateListViewModel @Inject constructor(private val apiHelper: ApiHelper) {

    val isLoading = MutableLiveData<Event<Boolean>>()
    val currencyRateList = MutableLiveData<List<CurrencyRates>>()





    fun getCurrencyList(amount: String, fromCurrency: String){
        CoroutineScope(Dispatchers.IO).launch {




        }
    }

}