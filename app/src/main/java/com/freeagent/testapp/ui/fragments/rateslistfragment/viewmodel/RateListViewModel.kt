package com.freeagent.testapp.ui.fragments.rateslistfragment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freeagent.testapp.api.ApiRepository
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.api.data.convertresponse.ConvertCurrencyRequest
import com.freeagent.testapp.api.data.timeseriesdata.RatesHistoricDataMapper
import com.freeagent.testapp.api.data.timeseriesdata.TimeSeriesRequest
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
    var listHistoricData = MutableLiveData<ArrayList<RatesHistoricDataMapper.CurrencyHistoricRate>>()

    /**
     * Recursive function to convert each currency
     *
     * @param amount amount to be converted
     * @param fromCurrency base currency
     * @param index index in AppCurrency Enum Liist
     */
    fun getCurrencyRecursive(amount: String, index: Int = 0) {

        if (index == AppCurrency.values().size) {
            completeRecursiveRequest(currencyRatesResponseList)
            return
        }

        val appCurrency = AppCurrency.values()[index]
        val request = ConvertCurrencyRequest(amount, appCurrency.name)

        if ( appCurrency.name == AppCurrency.EUR.name)  { //since EUR is base
            getCurrencyRecursive(amount, index + 1)
            return
        }

        viewModelScope.launch(Dispatchers.Main) {
            apiRepository.convertCurrency(request, object : OnCallBack<CurrencyRates?> {
                override fun onSuccess(response: CurrencyRates?) {
                    response?.let {  currencyRatesResponseList.add(response) }
                    getCurrencyRecursive(amount, index + 1)
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
    fun getCurrencyList(amount: String) {
        isLoading.postValue(Event(true))
        currencyRateList.value = arrayListOf()
        currencyRatesResponseList = arrayListOf()
        getCurrencyRecursive(amount)
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

    fun getCurrencyTimeSeries(timeSeriesRequest: TimeSeriesRequest){
        isLoading.postValue(Event(true))

        viewModelScope.launch(Dispatchers.Main) {
            apiRepository.getRatesTimeSeries(timeSeriesRequest, object: OnCallBack<ArrayList<RatesHistoricDataMapper.CurrencyHistoricRate>>{
                override fun onSuccess(response: ArrayList<RatesHistoricDataMapper.CurrencyHistoricRate>) {
                    response?.let { listHistoricData.value = response }
                    isLoading.postValue(Event(false))
                }

                override fun onError(message: String, errorCode: Int) {
                    isLoading.postValue(Event(false))
                }

            })
        }
    }

}