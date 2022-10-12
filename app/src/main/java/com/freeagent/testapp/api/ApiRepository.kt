package com.freeagent.testapp.api

import android.util.Log
import com.freeagent.testapp.api.apihelper.ApiHelper
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.api.data.convertresponse.ConvertCurrencyRequest
import com.freeagent.testapp.api.data.convertresponse.ConvertResponse
import com.freeagent.testapp.api.data.timeseriesdata.RatesHistoricDataMapper
import com.freeagent.testapp.api.data.timeseriesdata.TimeSeriesRequest
import com.freeagent.testapp.api.data.timeseriesdata.TimeSeriesResponse
import com.freeagent.testapp.utils.AppCurrency
import com.freeagent.testapp.utils.OnCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiHelper) {

    fun convertCurrency(request: ConvertCurrencyRequest, callback: OnCallBack<CurrencyRates?>){

            apiService.convertCurrency(request).enqueue(object : Callback<ConvertResponse> {
                override fun onResponse(call: Call<ConvertResponse>, response: Response<ConvertResponse>) {
                    val mappedResponse = mapCurrencyResponse(response.body())
                    if(mappedResponse == null){
                        callback.onError("something went wrong", response.code())
                    }
                    else{
                        callback.onSuccess(response = mappedResponse)
                    }
                }

                override fun onFailure(call: Call<ConvertResponse>, t: Throwable) {
                   callback.onError("Error", 0)
                }
            })

    }

    fun mapCurrencyResponse(response: ConvertResponse?): CurrencyRates?{
        if(response == null || response.success == false) return null
        var currencyRateModel: CurrencyRates? = null

        if(response.query?.to != null && response.result != null){
            val convertCurrencyEnum = AppCurrency.values().firstOrNull { appCurrency ->
                appCurrency.name == response.query.to
            }
            convertCurrencyEnum?.let { appCurrency ->
                currencyRateModel = CurrencyRates(appCurrency, response.result.toString())
            }
        }

        return currencyRateModel
    }

    fun getRatesTimeSeries(request: TimeSeriesRequest, callback: OnCallBack<ArrayList<RatesHistoricDataMapper.CurrencyHistoricRate>>){

        apiService.getTimeSeries(request).enqueue(object : Callback<TimeSeriesResponse>{
            override fun onResponse(call: Call<TimeSeriesResponse>, response: Response<TimeSeriesResponse>) {
                if(response.isSuccessful){
                    val listHistoricRates = RatesHistoricDataMapper.mapRatesHistoricData(response.body()?.rates)

                    if(listHistoricRates == null){
                        callback.onError("wrong data", response.code())
                    }
                    else{
                        callback.onSuccess(listHistoricRates)
                    }
                }
            }

            override fun onFailure(call: Call<TimeSeriesResponse>, t: Throwable) {
                callback.onError("call failed", 0)
            }

        })
    }

}

