package com.freeagent.testapp.ui.fragments.comparisonfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.freeagent.testapp.api.data.ConversionHistory
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.databinding.FragmentRateComparisonBinding
import com.freeagent.testapp.utils.AppCurrency
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RateComparisonFragment: Fragment() {

    private lateinit var binding: FragmentRateComparisonBinding
    private val args : RateComparisonFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRateComparisonBinding.inflate(inflater, container, false)


        val conversionRateOne = CurrencyRates(AppCurrency.EUR, "200")
        val conversionRateTwo = CurrencyRates(AppCurrency.GBP, "100")


        val dayOne = ConversionHistory.DatasetByDay("20/20/200", conversionRateOne , conversionRateTwo)
        val dayTwo = ConversionHistory.DatasetByDay("21/20/200", conversionRateOne , conversionRateTwo)
        val dayThree = ConversionHistory.DatasetByDay("22/20/200", conversionRateOne , conversionRateTwo)
        val dayFour = ConversionHistory.DatasetByDay("23/20/200", conversionRateOne , conversionRateTwo)
        val dayFive = ConversionHistory.DatasetByDay("24/20/200", conversionRateOne , conversionRateTwo)

        val conversionHistory = ConversionHistory(dayOne, dayTwo, dayThree, dayFour, dayFive)

        binding.dataset = conversionHistory

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedCurrency =  AppCurrency.values().first {
            it.name == args.comparisonFragArgsModel.selectedCurrency
        }

        val currencySymbol = Currency.getInstance(selectedCurrency.name).symbol
        binding.imgCurrencyFlag.setImageResource(selectedCurrency.imageResource)
        binding.tvCurrencyName.text = selectedCurrency.name
        binding.tvCurrencyValue.text = "$currencySymbol ${args.comparisonFragArgsModel.amount}"
    }

}