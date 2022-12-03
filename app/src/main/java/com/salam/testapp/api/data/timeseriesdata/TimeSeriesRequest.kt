package com.salam.testapp.api.data.timeseriesdata

data class TimeSeriesRequest(
    val endDate: String,
    val startDate: String,
    val symbols: String
)