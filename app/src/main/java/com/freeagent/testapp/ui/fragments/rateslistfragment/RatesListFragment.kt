package com.freeagent.testapp.ui.fragments.rateslistfragment

import android.R
import android.R.attr
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.freeagent.testapp.databinding.FragmentRatesListBinding
import com.freeagent.testapp.ui.fragments.rateslistfragment.adapters.RatesListAdapter
import com.freeagent.testapp.ui.fragments.rateslistfragment.viewmodel.RateListViewModel
import com.freeagent.testapp.utils.AppCurrency
import dagger.hilt.android.AndroidEntryPoint
import android.R.attr.country
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter


@AndroidEntryPoint
class RatesListFragment : Fragment() {

    private lateinit var binding: FragmentRatesListBinding
    private val viewModel: RateListViewModel by viewModels()
    private var spinnerList = arrayListOf<String>()
    private var selectedCurrency = ""
    private var selectedAmount = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRatesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppCurrency.values().forEach { spinnerList.add(it.name) }
        initObserver()

        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, spinnerList.toArray())
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.layoutCurrencySelect.spinnerCurrency.adapter = spinnerAdapter
        binding.layoutCurrencySelect.spinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCurrency = spinnerList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.layoutCurrencySelect.spinnerCurrency.setSelection(0)
            }
        }

        binding.layoutCurrencySelect.btnFetch.setOnClickListener{
            selectedAmount = binding.layoutCurrencySelect.tvAmount.text.toString()
            viewModel.getCurrencyList(selectedAmount, selectedCurrency)
        }

    }

    private fun initObserver(){

        viewModel.currencyRateList.observe(this.viewLifecycleOwner) {
            if (isVisible) {
                val adapter = RatesListAdapter(it)
                binding.ratesListRecycler.adapter = adapter
                adapter.notifyItemRangeChanged(0, it.lastIndex)
            }
        }

        viewModel.isLoading.observe(this.viewLifecycleOwner){ event ->
            event.getCoontentIfNotHandled()?.let {
                binding.layoutLoadingView.visibility = if(it) View.VISIBLE else View.GONE
            }
        }
    }

}