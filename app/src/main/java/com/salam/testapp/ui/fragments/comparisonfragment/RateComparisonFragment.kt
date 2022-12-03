package com.salam.testapp.ui.fragments.comparisonfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.salam.testapp.api.data.timeseriesdata.RatesHistoricDataMapper
import com.salam.testapp.api.data.timeseriesdata.TimeSeriesRequest
import com.salam.testapp.databinding.FragmentRateComparisonBinding
import com.salam.testapp.ui.fragments.rateslistfragment.viewmodel.RateListViewModel
import com.salam.testapp.ui.helper.HelperUtility
import com.salam.testapp.utils.AppCurrency
import com.salam.testapp.utils.TableGenerator
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class RateComparisonFragment: Fragment() {

    private lateinit var binding: FragmentRateComparisonBinding
    private val args : RateComparisonFragmentArgs by navArgs()
    private val viewModel: RateListViewModel by viewModels()
    private lateinit var tableGenerator: TableGenerator

    private var comparisonCurrencyOne =""
    private var comparisonCurrencyTwo =""

    data class TableRowData(var date: String = "", var currencyOneRate: String = "", var currencyTwoRate: String= "")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRateComparisonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tableGenerator = TableGenerator(requireContext())

        initObservers()

        comparisonCurrencyOne = args.comparisonFragArgsModel.exchangeRateCurrencyOne
        comparisonCurrencyTwo = args.comparisonFragArgsModel.exchangeRateCurrencyTow

        val currencySymbol = Currency.getInstance(AppCurrency.EUR.name).symbol
        binding.layoutBaseCurrency.imgCurrencyFlag.setImageResource(AppCurrency.EUR.imageResource)
        binding.layoutBaseCurrency.tvCurrencyName.text = AppCurrency.EUR.name
        binding.layoutBaseCurrency.tvCurrencyValue.text = "$currencySymbol ${args.comparisonFragArgsModel.amount}"

        if(HelperUtility.dummyTesting){
            val tableRows = mapTimeSeriesResponseToTableRows(HelperUtility.getDummyComparisonList())
            createHistoryTable(tableRows)
        }
        else{
            val request = TimeSeriesRequest(HelperUtility.getDate(), HelperUtility.getPreviousDate(), "$comparisonCurrencyOne,$comparisonCurrencyTwo")
            viewModel.getCurrencyTimeSeries(request)
        }
    }

    private fun initObservers(){
        viewModel.isLoading.observe(this.viewLifecycleOwner){ event ->
            event.getCoontentIfNotHandled()?.let {
                binding.layoutLoadingView.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        viewModel.listHistoricData.observe(this.viewLifecycleOwner) {
            if (isVisible) {
                val tableRows = mapTimeSeriesResponseToTableRows(it)
               createHistoryTable(tableRows)
            }
        }
    }

    /**
     * convert response data to table row data to populate on table
     * To make sure correct values are added to each field
     */
    private fun mapTimeSeriesResponseToTableRows(list :ArrayList<RatesHistoricDataMapper.CurrencyHistoricRate>): List<TableRowData>{
        val tableRowsList = arrayListOf<TableRowData>()
        list.forEach {
            val model = TableRowData()
            model.date = it.date.toString()
            it.currencyRate.forEach { rate ->
                if(rate.currencyName == comparisonCurrencyOne){
                    model.currencyOneRate = HelperUtility.getConvertedRate(rate.currencyRate, args.comparisonFragArgsModel.amount)
                }else if(rate.currencyName == comparisonCurrencyTwo){
                    model.currencyTwoRate = HelperUtility.getConvertedRate(rate.currencyRate, args.comparisonFragArgsModel.amount)
                }
            }
            tableRowsList.add(model)
        }
        return tableRowsList.toList()
    }

    /**
     * This will create rows below current table header
     *
     * @param list
     */
    private fun createHistoryTable(list :List<TableRowData>){
        binding.layoutCurrencyTable.currencyOneName.text = comparisonCurrencyOne
        binding.layoutCurrencyTable.currencyTwoName.text = comparisonCurrencyTwo
        binding.layoutCurrencyTable.tlConversionHistory.visibility = View.VISIBLE
        var index = 1
        list.forEach {
            val row = tableGenerator.generateRow(listOf(it.date, it.currencyOneRate, it.currencyTwoRate))
            binding.layoutCurrencyTable.tlConversionHistory.addView(row, index)
            index++
        }
    }

}