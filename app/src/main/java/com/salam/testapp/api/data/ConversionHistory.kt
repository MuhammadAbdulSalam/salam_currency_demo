package com.salam.testapp.api.data

import com.salam.testapp.utils.AppCurrency

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