package com.freeagent.testapp.ui.fragments.comparisonfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.freeagent.testapp.api.data.timeseriesdata.RatesHistoricDataMapper
import com.freeagent.testapp.api.data.timeseriesdata.TimeSeriesRequest
import com.freeagent.testapp.databinding.FragmentRateComparisonBinding
import com.freeagent.testapp.ui.fragments.rateslistfragment.viewmodel.RateListViewModel
import com.freeagent.testapp.ui.helper.HelperUtility
import com.freeagent.testapp.utils.AppCurrency
import com.freeagent.testapp.utils.TableGenerator
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class RateComparisonFragment: Fragment() {

    private lateinit var binding: FragmentRateComparisonBinding
    private val args : RateComparisonFragmentArgs by navArgs()
    private val viewModel: RateListViewModel by viewModels()
    private lateinit var tableGenerator: TableGenerator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRateComparisonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tableGenerator = TableGenerator(requireContext())

        val selectedCurrency =  AppCurrency.values().first {
            it.name == args.comparisonFragArgsModel.selectedCurrency
        }

        initObservers()

        val currencySymbol = Currency.getInstance(selectedCurrency.name).symbol
        binding.imgCurrencyFlag.setImageResource(selectedCurrency.imageResource)
        binding.tvCurrencyName.text = selectedCurrency.name
        binding.tvCurrencyValue.text = "$currencySymbol ${args.comparisonFragArgsModel.amount}"

        val request = TimeSeriesRequest(
            HelperUtility.getDate(),
            HelperUtility.getPreviousDate(),
             args.comparisonFragArgsModel.selectedCurrency,
            "${args.comparisonFragArgsModel.exchangeRateCurrencyOne},${args.comparisonFragArgsModel.exchangeRateCurrencyTow}"
        )

        viewModel.getCurrencyTimeSeries(request)
    }

    private fun initObservers(){
        viewModel.isLoading.observe(this.viewLifecycleOwner){ event ->
            event.getCoontentIfNotHandled()?.let {
                binding.layoutLoadingView.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        viewModel.listHistoricData.observe(this.viewLifecycleOwner) {
            if (isVisible) {
                createHistoryTable(it)
            }
        }
    }

    private fun createHistoryTable(list :ArrayList<RatesHistoricDataMapper.CurrencyHistoricRate>){
        binding.layoutCurrencyTable.currencyOneName.text = list[0].currencyRate[0].currencyName
        binding.layoutCurrencyTable.currencyTwoName.text = list[0].currencyRate[1].currencyName
        var index = 1
        list.forEach {
            val listTextViews = arrayListOf<String>()
            listTextViews.add(it.date.toString())
            it.currencyRate.forEach { rate-> listTextViews.add(HelperUtility.getConvertedRate(rate.currencyRate, args.comparisonFragArgsModel.amount)) }
            val row = tableGenerator.generateRow(listTextViews)
            binding.layoutCurrencyTable.tlConversionHistory.addView(row, index)
            index++
        }
    }

}