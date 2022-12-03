package com.salam.testapp.ui.fragments.rateslistfragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.salam.testapp.databinding.FragmentRatesListBinding
import com.salam.testapp.ui.fragments.rateslistfragment.adapters.RatesListAdapter
import com.salam.testapp.ui.fragments.rateslistfragment.viewmodel.RateListViewModel
import com.salam.testapp.utils.AppCurrency
import dagger.hilt.android.AndroidEntryPoint
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.salam.testapp.api.data.CurrencyRates
import com.salam.testapp.ui.fragments.rateslistfragment.adapters.ItemsDetailsLookup
import com.salam.testapp.ui.fragments.rateslistfragment.adapters.ItemsKeyProvider
import com.salam.testapp.ui.helper.fragnavdatamodels.ComparisonFragArgsModel
import org.json.JSONObject


@AndroidEntryPoint
class RatesListFragment : Fragment() {

    private lateinit var binding: FragmentRatesListBinding
    private lateinit var rateListAdapter: RatesListAdapter
    private lateinit var tracker: SelectionTracker<String>
    private val viewModel: RateListViewModel by viewModels()
    private var selectedAmount = ""

    private var currencyRatesList = listOf(
        CurrencyRates(AppCurrency.GBP, "110.0"),
        CurrencyRates(AppCurrency.AUD, "20.2"),
        CurrencyRates(AppCurrency.JPY, "231.0"),
        CurrencyRates(AppCurrency.USD, "100.1"),
        CurrencyRates(AppCurrency.NZD, "82.5"),
        CurrencyRates(AppCurrency.CAD, "10.2"),
        CurrencyRates(AppCurrency.CHF, "50.1"),
        CurrencyRates(AppCurrency.CNY, "40.1"),
        CurrencyRates(AppCurrency.SEK, "21.3"),
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRatesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        setupRecycler()

        binding.layoutCurrencySelect.btnFetch.setOnClickListener{
            selectedAmount = binding.layoutCurrencySelect.tvAmount.text.toString()
           // viewModel.getCurrencyList(selectedAmount) //use api response after testing
            rateListAdapter.setListItems(currencyRatesList)
        }
    }

    /**
     * Init observers to start observing currency lists and loading values
     */
    private fun initObserver(){

        viewModel.currencyRateList.observe(this.viewLifecycleOwner) {
            if (isVisible) {
                rateListAdapter.setListItems(it)
            }
        }

        viewModel.isLoading.observe(this.viewLifecycleOwner){ event ->
            event.getCoontentIfNotHandled()?.let {
                binding.layoutLoadingView.visibility = if(it) View.VISIBLE else View.GONE
            }
        }
    }

    /**
     * setup recycler and setup tracker to track selections
     */
    private fun setupRecycler(){

        rateListAdapter = RatesListAdapter()
        binding.ratesListRecycler.adapter = rateListAdapter

        tracker = SelectionTracker.Builder("selectionItem", binding.ratesListRecycler,
            ItemsKeyProvider(rateListAdapter), ItemsDetailsLookup(binding.ratesListRecycler),
            StorageStrategy.createStringStorage()).withSelectionPredicate(selectionPredicate).build()

        tracker.addObserver(selectionObserver)
        rateListAdapter.tracker = tracker
    }

    /**
     * set selection predicates in case added restriction for max selection 2
     */
    private val selectionPredicate = object : SelectionTracker.SelectionPredicate<String>() {
        override fun canSelectMultiple(): Boolean { return true }
        override fun canSetStateForKey(key: String, nextState: Boolean): Boolean { return !(nextState && tracker.selection.size() >= 2) }
        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean { return true }
    }

    /**
     * Tracker observer and set show history button
     */
    private val selectionObserver = object : SelectionTracker.SelectionObserver<String>() {
        override fun onSelectionChanged() {
            super.onSelectionChanged()
            binding.layoutCurrencySelect.btnHistory.visibility = if(tracker.selection.size() ==2) View.VISIBLE else View.INVISIBLE
            binding.layoutCurrencySelect.btnHistory.setOnClickListener{
                val fragArgs = ComparisonFragArgsModel(
                    amount = selectedAmount,
                    exchangeRateCurrencyOne = tracker.selection.elementAt(0),
                    exchangeRateCurrencyTow = tracker.selection.elementAt(1)
                )
                findNavController().navigate(RatesListFragmentDirections.actionFragmentHomeScreenToFragmentComparison(fragArgs))
            }
        }
    }

}