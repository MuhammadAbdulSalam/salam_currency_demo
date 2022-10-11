package com.freeagent.testapp.ui.fragments.rateslistfragment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freeagent.testapp.api.ApiRepository
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.api.data.convertresponse.ConvertCurrencyRequest
import com.freeagent.testapp.utils.AppCurrency
import com.freeagent.testapp.utils.Event
import com.freeagent.testapp.utils.OnCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RateListViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {

    val isLoading = MutableLiveData<Event<Boolean>>()
    val currencyRateList = MutableLiveData<List<CurrencyRates>>()
    var currencyRatesResponseList = arrayListOf<CurrencyRates>()




    fun getCurrencyRecursive(amount: String, fromCurrency: String, index: Int = 0){
        if(index < AppCurrency.values().size){
            val appCurrency = AppCurrency.values()[index]
            val request = ConvertCurrencyRequest(amount, fromCurrency, appCurrency.name)

            if(request.from != appCurrency.name){
                apiRepository.convertCurrency(
                    request,
                    object : OnCallBack<CurrencyRates?> {

                        override fun onSuccess(response: CurrencyRates?) {
                            if (response != null) {
                                currencyRatesResponseList.add(response)
                                getCurrencyRecursive(amount, fromCurrency, index+1)
                            }
                        }

                        override fun onError(message: String, errorCode: Int) {
                            isLoading.postValue(Event(true))
                        }
                    })
            }
            else
            {
                getCurrencyRecursive(amount, fromCurrency, index+1)
            }

        }
        else{
            completeRecursiveRequest(currencyRatesResponseList)
        }

    }

    fun getCurrencyList(amount: String, fromCurrency: String){
        isLoading.postValue(Event(true))
        currencyRateList.value = arrayListOf()
        currencyRatesResponseList = arrayListOf()
        getCurrencyRecursive(amount, fromCurrency)

    }

    private fun completeRecursiveRequest(currecyList: ArrayList<CurrencyRates>){
        isLoading.postValue(Event(false))
        currencyRateList.value = currecyList
    }

}