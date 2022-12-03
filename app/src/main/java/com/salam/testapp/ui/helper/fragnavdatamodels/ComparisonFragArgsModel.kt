package com.salam.testapp.ui.helper.fragnavdatamodels

import java.io.Serializable

data class ComparisonFragArgsModel(
    val amount: String,
    val exchangeRateCurrencyOne: String,
    val exchangeRateCurrencyTow: String
    ): Serializable