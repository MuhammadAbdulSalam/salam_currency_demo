package com.salam.testapp.api

import com.salam.testapp.BuildConfig
import com.salam.testapp.api.data.apiconstants.Constants
import com.salam.testapp.api.data.apiconstants.Constants.ENDPOINT_CONVERT
import com.salam.testapp.api.data.apiconstants.Constants.ENDPOINT_TIMESERIES
import com.salam.testapp.api.data.convertresponse.ConvertResponse
import com.salam.testapp.api.data.timeseriesdata.TimeSeriesResponse
import com.salam.testapp.utils.AppCurrency
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RetrofitBuilder {

    @GET(ENDPOINT_CONVERT)
    fun convertCurrency(
        @Header("apikey") apiKey: String = Constants.API_KEY,
        @Query("amount") amount: String,
        @Query("from") fromCurrency: String = AppCurrency.EUR.name,
        @Query("to") toCurrency: String,
    ): Call<ConvertResponse>

    @GET(ENDPOINT_TIMESERIES)
    fun getTimeSeries(
        @Header("apikey") apiKey: String = Constants.API_KEY,
        @Query("end_date") endDate: String,
        @Query("start_date") startDate: String,
        @Query("base") base: String = AppCurrency.EUR.name,
        @Query("symbols") symbols: String,
        ): Call<TimeSeriesResponse>

    /**
     * Retrofit builder component
     */
    companion object {
        private var gson = GsonBuilder()
            .setLenient()
            .create()

        private val logger =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        private val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .callTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()

        fun create(): RetrofitBuilder {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RetrofitBuilder::class.java)
        }
    }

}