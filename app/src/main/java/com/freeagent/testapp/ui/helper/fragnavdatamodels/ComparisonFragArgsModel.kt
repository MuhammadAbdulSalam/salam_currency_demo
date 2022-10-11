package com.freeagent.testapp.ui.helper.fragnavdatamodels

import java.io.Serializable

data class ComparisonFragArgsModel(
    val amount: String,
    val selectedCurrency: String,
    val exchangeRateCurrencyOne: String,
    val exchangeRateCurrencyTow: String
    ): Serializable