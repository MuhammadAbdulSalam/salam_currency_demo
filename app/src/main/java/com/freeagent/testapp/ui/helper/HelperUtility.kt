package com.freeagent.testapp.ui.helper

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object HelperUtility {

    fun getDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun minusFiveDays(): Date {
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
}