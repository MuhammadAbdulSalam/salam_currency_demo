package com.freeagent.testapp.ui.fragments.rateslistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.databinding.FragmentRatesListBinding
import com.freeagent.testapp.ui.fragments.rateslistfragment.adapters.RatesListAdapter
import com.freeagent.testapp.utils.AppCurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatesListFragment : Fragment() {

    lateinit var binding: FragmentRatesListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRatesListBinding.inflate(inflater, container, false)

        val listCurrency = arrayListOf<CurrencyRates>()
        listCurrency.add(CurrencyRates(AppCurrency.AUD, "100.00"))
        listCurrency.add(CurrencyRates(AppCurrency.GBP, "10.00"))
        listCurrency.add(CurrencyRates(AppCurrency.SEK, "120.00"))
        listCurrency.add(CurrencyRates(AppCurrency.USD, "130.00"))
        listCurrency.add(CurrencyRates(AppCurrency.JPY, "140.00"))
        listCurrency.add(CurrencyRates(AppCurrency.CAD, "110.00"))

        val adapter  = RatesListAdapter(listCurrency)
        binding.ratesListRecycler.adapter = adapter
        adapter.notifyDataSetChanged()

        return binding.root
    }
}