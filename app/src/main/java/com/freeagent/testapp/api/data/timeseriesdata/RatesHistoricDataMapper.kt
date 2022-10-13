package com.freeagent.testapp.api.data.timeseriesdata

import com.google.gson.JsonObject
import org.json.JSONObject

object RatesHistoricDataMapper {

    data class CurrencyHistoricRate(
        var date: String? = null,
        var currencyRate: ArrayList<Currencies> = ArrayList()
    ) {
        data class Currencies(
            var currencyName: String,
            var currencyRate: String
        )
    }

    fun mapRatesHistoricData(ratesResponse: JsonObject?): ArrayList<CurrencyHistoricRate>?{
        if (ratesResponse == null) return null
        val rates = JSONObject(ratesResponse.toString())
        val arrayOfHistoricData = ArrayList<CurrencyHistoricRate>()
        rates.keys().forEach {
            val currencyHistoricRate = CurrencyHistoricRate()
            currencyHistoricRate.date = it.toString()

            val currencies = JSONObject(rates.get(it).toString())
            currencies.keys().forEach { key->
                val currencyValue = CurrencyHistoricRate.Currencies(key, currencies.get(key).toString())
                currencyHistoricRate.currencyRate.add(currencyValue)
            }
            arrayOfHistoricData.add(currencyHistoricRate)
        }

        return arrayOfHistoricData
    }
}