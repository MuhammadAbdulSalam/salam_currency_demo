package com.freeagent.testapp.api.apihelper

import com.freeagent.testapp.api.RetrofitBuilder
import com.freeagent.testapp.api.data.convertresponse.ConvertCurrencyRequest
import com.freeagent.testapp.api.data.timeseriesdata.TimeSeriesRequest
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: RetrofitBuilder) {

    fun convertCurrency(convertCurrencyRequest: ConvertCurrencyRequest) =
        apiService.convertCurrency(
            amount = convertCurrencyRequest.amount,
            fromCurrency = convertCurrencyRequest.from,
            toCurrency = convertCurrencyRequest.to
        )

    fun getTimeSeries(timeSeriesRequest: TimeSeriesRequest) =
        apiService.getTimeSeries(
            endDate = timeSeriesRequest.endDate,
            startDate = timeSeriesRequest.startDate,
            base = timeSeriesRequest.base,
            symbols = timeSeriesRequest.symbols
        )

}