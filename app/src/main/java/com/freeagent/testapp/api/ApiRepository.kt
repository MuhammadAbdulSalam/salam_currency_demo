package com.freeagent.testapp.api

import com.freeagent.testapp.api.data.convertresponse.ConvertCurrencyRequest
import com.freeagent.testapp.api.data.convertresponse.ConvertResponse
import com.freeagent.testapp.utils.OnCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: RetrofitBuilder) {

    fun convertCurrency(request: ConvertCurrencyRequest, callback: OnCallBack<ConvertResponse?>){

        CoroutineScope(Dispatchers.IO).launch {
            apiService.convertCurrency(
                amount = request.amount,
                fromCurrency = request.from,
                toCurrency = request.to
            ).enqueue(object : Callback<ConvertResponse> {
                override fun onResponse(call: Call<ConvertResponse>, response: Response<ConvertResponse>) {
                   callback.onSuccess(response = response.body())
                }

                override fun onFailure(call: Call<ConvertResponse>, t: Throwable) {
                   callback.onError("Error", 0)
                }
            })
        }

    }

}

