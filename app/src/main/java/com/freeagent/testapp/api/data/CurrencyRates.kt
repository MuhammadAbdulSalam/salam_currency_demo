package com.freeagent.testapp.api.data

import com.freeagent.testapp.utils.Currency

data class CurrencyRates(
    val currency: Currency,
    val rate: String
)