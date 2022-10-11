package com.freeagent.testapp.ui.fragments.rateslistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.freeagent.testapp.databinding.FragmentRatesListBinding
import com.freeagent.testapp.ui.fragments.rateslistfragment.adapters.RatesListAdapter
import com.freeagent.testapp.ui.fragments.rateslistfragment.viewmodel.RateListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatesListFragment : Fragment() {

    lateinit var binding: FragmentRatesListBinding
    val viewModel: RateListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatesListBinding.inflate(inflater, container, false)

        viewModel.getCurrencyList("20", "GBP")

        viewModel.currencyRateList.observe(this.viewLifecycleOwner) {
            if (isVisible) {
                val adapter = RatesListAdapter(it)
                binding.ratesListRecycler.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }


        return binding.root
    }
}