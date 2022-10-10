package com.freeagent.testapp.api.data.convertresponse

data class ConvertCurrencyRequest(
    val amount: String,
    val from: String,
    val to: String
)