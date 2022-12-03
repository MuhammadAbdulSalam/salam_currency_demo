package com.salam.testapp.datamodeltests

import com.salam.testapp.BaseTestClass
import com.salam.testapp.api.data.timeseriesdata.RatesHistoricDataMapper
import com.salam.testapp.api.data.timeseriesdata.TimeSeriesResponse
import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test

class TimeseriesDataTest: BaseTestClass(){

    val ratesLocation = "../app/src/main/assets/jsondata/timeseriesResponse.json"

    @Test
    fun `test timeseries response mapping check fields`(){
        val timeSeriesModel = Gson().fromJson(loadJSONFromAsset(ratesLocation), TimeSeriesResponse::class.java)

        Assert.assertNotNull(timeSeriesModel)
        Assert.assertEquals(timeSeriesModel.base, "EUR")
        Assert.assertEquals(timeSeriesModel.end_date, "2012-05-03")
        Assert.assertNotNull(timeSeriesModel.rates)
    }

    @Test
    fun `test timeseries rates mapping `(){
        val timeSeriesModel = Gson().fromJson(loadJSONFromAsset(ratesLocation), TimeSeriesResponse::class.java)
        val ratesMap = RatesHistoricDataMapper.mapRatesHistoricData(timeSeriesModel.rates)
        Assert.assertNotNull(ratesMap)
        Assert.assertEquals(ratesMap?.size, 3)
        Assert.assertEquals(ratesMap!![0].date, "2012-05-03")
        Assert.assertEquals(ratesMap[0].currencyRate[0].currencyName, "AUD")
        Assert.assertEquals(ratesMap[0].currencyRate[0].currencyRate, "1.280135")
        Assert.assertEquals(ratesMap[0].currencyRate[1].currencyName, "CAD")
        Assert.assertEquals(ratesMap[0].currencyRate[1].currencyRate, "1.296868")
        Assert.assertEquals(ratesMap[0].currencyRate[2].currencyName, "USD")
        Assert.assertEquals(ratesMap[0].currencyRate[2].currencyRate, "1.314491")

        Assert.assertEquals(ratesMap[1].date, "2012-05-02")
        Assert.assertEquals(ratesMap[1].currencyRate[0].currencyName, "AUD")
        Assert.assertEquals(ratesMap[1].currencyRate[0].currencyRate, "1.274202")
        Assert.assertEquals(ratesMap[1].currencyRate[1].currencyName, "CAD")
        Assert.assertEquals(ratesMap[1].currencyRate[1].currencyRate, "1.299083")
        Assert.assertEquals(ratesMap[1].currencyRate[2].currencyName, "USD")
        Assert.assertEquals(ratesMap[1].currencyRate[2].currencyRate, "1.315066")

        Assert.assertEquals(ratesMap[2].date, "2012-05-01")
        Assert.assertEquals(ratesMap[2].currencyRate[0].currencyName, "AUD")
        Assert.assertEquals(ratesMap[2].currencyRate[0].currencyRate, "1.278047")
        Assert.assertEquals(ratesMap[2].currencyRate[1].currencyName, "CAD")
        Assert.assertEquals(ratesMap[2].currencyRate[1].currencyRate, "1.302303")
        Assert.assertEquals(ratesMap[2].currencyRate[2].currencyName, "USD")
        Assert.assertEquals(ratesMap[2].currencyRate[2].currencyRate, "1.322891")
    }


}