package com.freeagent.testapp.ui.fragments.rateslistfragment.adapters

import androidx.recyclerview.selection.ItemKeyProvider

class ItemsKeyProvider(private val adapter: RatesListAdapter) : ItemKeyProvider<String>(SCOPE_CACHED) {

    override fun getKey(position: Int): String =
        adapter.currencyRatesList[position].appCurrency.name

    override fun getPosition(key: String): Int =
        adapter.currencyRatesList.indexOfFirst { it.appCurrency.name == key }
}