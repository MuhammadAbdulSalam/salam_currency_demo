package com.freeagent.testapp.api.data.timeseriesdata

data class TimeSeriesRequest(
    val endDate: String,
    val startDate: String,
    val base: String,
    val symbols: String
)