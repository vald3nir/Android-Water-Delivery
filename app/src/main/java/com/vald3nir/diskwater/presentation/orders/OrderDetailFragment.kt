package com.vald3nir.diskwater.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.databinding.FragmentOrderDetailBinding
import com.vald3nir.toolkit.extensions.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailFragment : BaseFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentOrderDetailBinding

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
        binding.setupObservers()
        viewModel.loadData(this)
    }

    private fun FragmentOrderDetailBinding.initViews() {
        toolbar.setupToolbar(
            activity = activity,
            title = "Detalhe do Pedido"
        )
    }

    private fun FragmentOrderDetailBinding.setupObservers() {
    }
}

