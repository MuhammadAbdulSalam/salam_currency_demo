package com.freeagent.testapp.api.apihelper

import com.freeagent.testapp.api.RetrofitBuilder
import com.freeagent.testapp.api.data.convertresponse.ConvertCurrencyRequest
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: RetrofitBuilder) {

    fun convertCurrency(convertCurrencyRequest: ConvertCurrencyRequest) =
        apiService.convertCurrency(
            amount = convertCurrencyRequest.amount,
            fromCurrency = convertCurrencyRequest.from,
            toCurrency = convertCurrencyRequest.to
        )

}