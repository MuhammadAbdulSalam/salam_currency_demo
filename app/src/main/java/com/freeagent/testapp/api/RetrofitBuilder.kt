package com.freeagent.testapp.api

import com.freeagent.testapp.api.data.convertresponse.ConvertResponse
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

    @GET("convert")
    fun convertCurrency(
        @Header("apikey") apiKey: String = "Io8uZmzHMU9Xw7annfPkln1eFqm1FeOy",
        @Query("amount") amount: String,
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String,
    ): Call<ConvertResponse>




    /**
     * Retrofit builder component
     */
    companion object {
        private const val BASE_URL = "https://api.apilayer.com/fixer/"
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
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RetrofitBuilder::class.java)
        }
    }

}