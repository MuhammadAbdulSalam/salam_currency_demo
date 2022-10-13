package com.freeagent.testapp.api.data

import com.freeagent.testapp.utils.AppCurrency

data class ConversionHistory(
    val dayOne: DatasetByDay,
    val dayTwo: DatasetByDay,
    val dayThree: DatasetByDay,
    val dayFour: DatasetByDay,
    val dayFive: DatasetByDay,
    ) {

    data class DatasetByDay(
        val date: String,
        val conversionRatesOne: CurrencyRates,
        val conversionRatesTwo: CurrencyRates
    )
}