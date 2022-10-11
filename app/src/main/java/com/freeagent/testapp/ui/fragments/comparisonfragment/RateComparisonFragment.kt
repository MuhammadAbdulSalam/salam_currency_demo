package com.freeagent.testapp.ui.fragments.comparisonfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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