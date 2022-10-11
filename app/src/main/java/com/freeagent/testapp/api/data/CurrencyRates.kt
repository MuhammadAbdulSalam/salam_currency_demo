package com.freeagent.testapp.api.data

import com.freeagent.testapp.utils.AppCurrency
import java.util.*

data class CurrencyRates(
    var appCurrency: AppCurrency,
    var rate: String,
    val symbol: String = Currency.getInstance(appCurrency.name).symbol
)