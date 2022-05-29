package com.vald3nir.diskwater.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.toolkit.core.CoreFragment
import com.vald3nir.diskwater.databinding.FragmentProductDetailBinding
import com.vald3nir.diskwater.presentation.orders.OrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : CoreFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        viewModel.appView = appView
        binding.apply {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}