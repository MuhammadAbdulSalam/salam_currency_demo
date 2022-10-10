package com.freeagent.testapp.ui.fragments.rateslistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.freeagent.testapp.databinding.FragmentRatesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatesListFragment : Fragment() {

    lateinit var binding: FragmentRatesListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRatesListBinding.inflate(inflater, container, false)
        return binding.root
    }
}