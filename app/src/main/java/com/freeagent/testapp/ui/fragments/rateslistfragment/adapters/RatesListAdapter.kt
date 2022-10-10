package com.freeagent.testapp.ui.fragments.rateslistfragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.databinding.LayoutItemCurrencyRateBinding
import java.util.*

class RatesListAdapter constructor(val currencyList: List<CurrencyRates>) : RecyclerView.Adapter<RatesListAdapter.RatesListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesListViewHolder {
        val binding = LayoutItemCurrencyRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatesListViewHolder, position: Int) {
        val currencyRateModel = currencyList[position]
        holder.binding.tvCurrencyName.text = currencyRateModel.appCurrency.name
        holder.binding.imgCurrencyFlag.setImageResource(currencyRateModel.appCurrency.imageResource)
        holder.binding.tvCurrencyValue.text = "${currencyRateModel.symbol} ${currencyRateModel.rate}"
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    class RatesListViewHolder(val binding: LayoutItemCurrencyRateBinding): RecyclerView.ViewHolder(binding.root)



}