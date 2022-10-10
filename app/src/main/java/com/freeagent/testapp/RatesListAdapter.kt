package com.freeagent.testapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.freeagent.testapp.databinding.ItemViewCurrencyBinding

class RatesListAdapter : RecyclerView.Adapter<RatesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemViewCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0 //TODO: Replace this with the actual count
    }

    class ViewHolder(private val binding: ItemViewCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}