package com.freeagent.testapp.ui.fragments.rateslistfragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.databinding.LayoutItemCurrencyRateBinding
import com.freeagent.testapp.utils.Currency

class RatesListAdapter constructor(val currencyList: List<CurrencyRates>) : RecyclerView.Adapter<RatesListAdapter.RatesListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesListViewHolder {
        val binding = LayoutItemCurrencyRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatesListViewHolder, position: Int) {
        val currencyRateModel = currencyList[position]
        holder.binding.currency = currencyRateModel.currency
        holder.binding.tvCurrencyValue.text = "${currencyRateModel.currency.symbol} ${currencyRateModel.rate}"
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    class RatesListViewHolder(val binding: LayoutItemCurrencyRateBinding): RecyclerView.ViewHolder(binding.root)



}