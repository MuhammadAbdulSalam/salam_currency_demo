package com.salam.testapp.api.data.timeseriesdata

import com.google.gson.JsonObject
import java.io.Serializable

data class TimeSeriesResponse(
    val base: String,
    val end_date: String,
    val rates: JsonObject,
    val start_date: String,
    val success: Boolean,
    val timeseries: Boolean
): Serializable