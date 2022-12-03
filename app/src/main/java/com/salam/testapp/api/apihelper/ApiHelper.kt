package com.salam.testapp.api.apihelper

import com.salam.testapp.api.RetrofitBuilder
import com.salam.testapp.api.data.convertresponse.ConvertCurrencyRequest
import com.salam.testapp.api.data.timeseriesdata.TimeSeriesRequest
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: RetrofitBuilder) {

    fun convertCurrency(convertCurrencyRequest: ConvertCurrencyRequest) =
        apiService.convertCurrency(
            amount = convertCurrencyRequest.amount,
            toCurrency = convertCurrencyRequest.to
        )

    fun getTimeSeries(timeSeriesRequest: TimeSeriesRequest) =
        apiService.getTimeSeries(
            endDate = timeSeriesRequest.endDate,
            startDate = timeSeriesRequest.startDate,
            symbols = timeSeriesRequest.symbols
        )

}