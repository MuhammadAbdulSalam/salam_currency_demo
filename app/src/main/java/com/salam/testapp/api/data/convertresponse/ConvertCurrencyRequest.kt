package com.salam.testapp.api.data.convertresponse


data class ConvertCurrencyRequest(
    val amount: String,
    val to: String
)