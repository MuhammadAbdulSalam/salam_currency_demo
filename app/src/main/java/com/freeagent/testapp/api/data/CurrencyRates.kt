package com.freeagent.testapp.api.data

import com.freeagent.testapp.utils.AppCurrency
import java.util.*

data class CurrencyRates(
    val appCurrency: AppCurrency,
    val rate: String,
    val symbol: String = Currency.getInstance(appCurrency.name).symbol
)