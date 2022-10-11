package com.freeagent.testapp.ui.fragments.rateslistfragment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freeagent.testapp.api.ApiRepository
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.api.data.convertresponse.ConvertCurrencyRequest
import com.freeagent.testapp.utils.AppCurrency
import com.freeagent.testapp.utils.Event
import com.freeagent.testapp.utils.OnCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RateListViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    val isLoading = MutableLiveData<Event<Boolean>>()
    val currencyRateList = MutableLiveData<List<CurrencyRates>>()
    var currencyRatesResponseList = arrayListOf<CurrencyRates>()

    /**
     * Recursive function to convert each currency
     *
     * @param amount amount to be converted
     * @param fromCurrency base currency
     * @param index index in AppCurrency Enum Liist
     */
    fun getCurrencyRecursive(amount: String, fromCurrency: String, index: Int = 0) {

        if (index == AppCurrency.values().size) {
            completeRecursiveRequest(currencyRatesResponseList)
            return
        }

        val appCurrency = AppCurrency.values()[index]
        val request = ConvertCurrencyRequest(amount, fromCurrency, appCurrency.name)

        if (request.from == appCurrency.name)  {
            getCurrencyRecursive(amount, fromCurrency, index + 1)
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            apiRepository.convertCurrency(request, object : OnCallBack<CurrencyRates?> {
                override fun onSuccess(response: CurrencyRates?) {
                    response?.let {  currencyRatesResponseList.add(response) }
                    getCurrencyRecursive(amount, fromCurrency, index + 1)
                }

                override fun onError(message: String, errorCode: Int) {
                    isLoading.postValue(Event(true))
                }
            })
        }
    }

    /**
     * get Currency list, Initial recursive and then wait for calls to complete
     *
     * @param amount amount to be converted
     * @param fromCurrency base currency
     */
    fun getCurrencyList(amount: String, fromCurrency: String) {
        isLoading.postValue(Event(true))
        currencyRateList.value = arrayListOf()
        currencyRatesResponseList = arrayListOf()
        getCurrencyRecursive(amount, fromCurrency)
    }

    /**
     * Currency list updated, Now update livedata to update observers
     *
     * @param currencyList respnse currency list mapped from api response
     */
    private fun completeRecursiveRequest(currencyList: ArrayList<CurrencyRates>) {
        isLoading.postValue(Event(false))
        currencyRateList.value = currencyList
    }

}