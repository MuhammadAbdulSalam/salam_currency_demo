package com.salam.testapp.ui.helper

import com.salam.testapp.api.data.CurrencyRates
import com.salam.testapp.api.data.timeseriesdata.RatesHistoricDataMapper
import com.salam.testapp.utils.AppCurrency
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object HelperUtility {

    const val dummyTesting = true

    fun getDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    private fun minusFiveDays(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -5)
        return cal.time
    }

    fun getPreviousDate(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(minusFiveDays())
    }

    fun getConvertedRate(rate: String, amount: String): String {
        val conversionValue = rate.toDouble() * amount.toDouble()
        val df = DecimalFormat("0.00")
        return df.format(conversionValue)
    }

    fun getDummyComparisonList(): ArrayList<RatesHistoricDataMapper.CurrencyHistoricRate> {
        return arrayListOf(
            RatesHistoricDataMapper.CurrencyHistoricRate(
                date = "2022-01-01",
                currencyRate = arrayListOf(
                    RatesHistoricDataMapper.CurrencyHistoricRate.Currencies(AppCurrency.GBP.name,"120"),
                    RatesHistoricDataMapper.CurrencyHistoricRate.Currencies(AppCurrency.AUD.name,"10"),
                )
            ),

            RatesHistoricDataMapper.CurrencyHistoricRate(
                date = "2022-01-02",
                currencyRate = arrayListOf(
                    RatesHistoricDataMapper.CurrencyHistoricRate.Currencies(AppCurrency.GBP.name,"110"),
                    RatesHistoricDataMapper.CurrencyHistoricRate.Currencies(AppCurrency.AUD.name,"5"),
                )
            ),

            RatesHistoricDataMapper.CurrencyHistoricRate(
                date = "2022-01-03",
                currencyRate = arrayListOf(
                    RatesHistoricDataMapper.CurrencyHistoricRate.Currencies(AppCurrency.GBP.name,"87.0"),
                    RatesHistoricDataMapper.CurrencyHistoricRate.Currencies(AppCurrency.AUD.name,"2.2"),
                )
            ),

            RatesHistoricDataMapper.CurrencyHistoricRate(
                date = "2022-01-04",
                currencyRate = arrayListOf(
                    RatesHistoricDataMapper.CurrencyHistoricRate.Currencies(AppCurrency.GBP.name,"45.0"),
                    RatesHistoricDataMapper.CurrencyHistoricRate.Currencies(AppCurrency.AUD.name,"54.2"),
                )
            ),

            RatesHistoricDataMapper.CurrencyHistoricRate(
                date = "2022-01-05",
                currencyRate = arrayListOf(
                    RatesHistoricDataMapper.CurrencyHistoricRate.Currencies(AppCurrency.GBP.name,"22.0"),
                    RatesHistoricDataMapper.CurrencyHistoricRate.Currencies(AppCurrency.AUD.name,"3.2"),
                )
            ),
        )
    }

    fun getDummyCurrencyList(): List<CurrencyRates> {
        return listOf(
            CurrencyRates(AppCurrency.GBP, "110.0"),
            CurrencyRates(AppCurrency.AUD, "20.2"),
            CurrencyRates(AppCurrency.JPY, "231.0"),
            CurrencyRates(AppCurrency.USD, "100.1"),
            CurrencyRates(AppCurrency.NZD, "82.5"),
            CurrencyRates(AppCurrency.CAD, "10.2"),
            CurrencyRates(AppCurrency.CHF, "50.1"),
            CurrencyRates(AppCurrency.CNY, "40.1"),
            CurrencyRates(AppCurrency.SEK, "21.3"),
        )
    }
}