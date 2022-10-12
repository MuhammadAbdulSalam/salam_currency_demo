package com.freeagent.testapp.ui.fragments.rateslistfragment

import android.R
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
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.ui.fragments.rateslistfragment.adapters.ItemsDetailsLookup
import com.freeagent.testapp.ui.fragments.rateslistfragment.adapters.ItemsKeyProvider
import com.freeagent.testapp.ui.helper.fragnavdatamodels.ComparisonFragArgsModel
import org.json.JSONObject


@AndroidEntryPoint
class RatesListFragment : Fragment() {

    private lateinit var binding: FragmentRatesListBinding
    private lateinit var rateListAdapter: RatesListAdapter
    private lateinit var tracker: SelectionTracker<String>

    private var spinnerList = arrayListOf<String>()
    private var selectedAmount = ""

    val currencyList = listOf<CurrencyRates>(
        CurrencyRates(appCurrency = AppCurrency.GBP, rate = "20"),
        CurrencyRates(appCurrency = AppCurrency.JPY, rate = "20"),
        CurrencyRates(appCurrency = AppCurrency.EUR, rate = "20")
    )

    private val viewModel: RateListViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRatesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppCurrency.values().forEach { spinnerList.add(it.name) }
        initObserver()
        setupRecycler()

        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, spinnerList.toArray())
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.layoutCurrencySelect.btnFetch.setOnClickListener{
            selectedAmount = binding.layoutCurrencySelect.tvAmount.text.toString()
            viewModel.getCurrencyList(selectedAmount)
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

    private val selectionPredicate = object : SelectionTracker.SelectionPredicate<String>() {
        override fun canSelectMultiple(): Boolean { return true }
        override fun canSetStateForKey(key: String, nextState: Boolean): Boolean { return !(nextState && tracker.selection.size() >= 2) }
        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean { return true }
    }


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
                findNavController().navigate(RatesListFragmentDirections.actionFragmentSplashScreenToFragmentLogin(fragArgs))
            }
        }
    }

}