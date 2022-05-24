package com.vald3nir.diskwater.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.common.core.BaseFragment
import com.vald3nir.diskwater.common.extensions.setupToolbar
import com.vald3nir.diskwater.databinding.FragmentOrderDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailFragment : BaseFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentOrderDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            toolbar.setupToolbar(
                activity = activity,
                title = "Detalhe do Pedido"
            )
        }
    }
}