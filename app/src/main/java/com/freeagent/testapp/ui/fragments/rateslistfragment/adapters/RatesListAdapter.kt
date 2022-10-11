package com.freeagent.testapp.ui.fragments.rateslistfragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.freeagent.testapp.R
import com.freeagent.testapp.api.data.CurrencyRates
import com.freeagent.testapp.databinding.LayoutItemCurrencyRateBinding


class RatesListAdapter : RecyclerView.Adapter<RatesListAdapter.RatesListViewHolder>() {

    private lateinit var binding: LayoutItemCurrencyRateBinding

    var currencyRatesList: List<CurrencyRates> = emptyList()
        private set

    var tracker: SelectionTracker<String>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RatesListViewHolder {
        binding = LayoutItemCurrencyRateBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return RatesListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: RatesListViewHolder, pos: Int) {
        val item = currencyRatesList[pos]
        viewHolder.bind(item)
    }

    override fun getItemCount(): Int {
        return currencyRatesList.size
    }

    fun setListItems(list: List<CurrencyRates>) {
        currencyRatesList = list
        notifyItemRangeChanged(0, list.size)
    }

    inner class RatesListViewHolder(private val layoutBinding: LayoutItemCurrencyRateBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {

        fun bind(currency: CurrencyRates) {
            layoutBinding.tvCurrencyName.text = currency.appCurrency.name
            layoutBinding.imgCurrencyFlag.setImageResource(currency.appCurrency.imageResource)
            layoutBinding.tvCurrencyValue.text = "${currency.symbol} ${currency.rate}"

            tracker?.let {

                if (it.isSelected(currency.appCurrency.name)) {
                    layoutBinding.layoutParentView.setCardBackgroundColor(
                        ContextCompat.getColor(layoutBinding.layoutParentView.context, R.color.purple_200))
                } else {
                    layoutBinding.layoutParentView.setCardBackgroundColor(
                        ContextCompat.getColor(layoutBinding.layoutParentView.context, R.color.white))
                }
            }
        }

        fun getItem(): ItemDetailsLookup.ItemDetails<String> = object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): String = currencyRatesList[bindingAdapterPosition].appCurrency.name
            }
    }

}