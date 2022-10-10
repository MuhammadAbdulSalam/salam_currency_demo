package com.freeagent.testapp.api.data.convertresponse

import java.io.Serializable

data class ConvertResponse(

    val date: String,
    val historical: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean

) : Serializable {

    data class Info(
        val rate: Double,
        val timestamp: Long
    ) : Serializable

    data class Query(
        val amount: Int,
        val from: String,
        val to: String
    ) : Serializable


}


