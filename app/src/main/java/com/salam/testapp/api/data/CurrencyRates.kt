package com.salam.testapp.api.data

import com.salam.testapp.utils.AppCurrency
import java.util.*

data class CurrencyRates(
    var appCurrency: AppCurrency,
    var rate: String,
    val symbol: String = Currency.getInstance(appCurrency.name).symbol,
    val isSelected: Boolean = false
)