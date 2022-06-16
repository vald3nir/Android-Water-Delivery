package com.vald3nir.diskwater.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.databinding.FragmentItemsOrderBinding
import com.vald3nir.toolkit.extensions.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemsOrderFragment : BaseFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentItemsOrderBinding

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
        binding.setupObservers()
    }

    private fun FragmentItemsOrderBinding.initViews() {
        toolbar.setupToolbar(
            activity = activity,
            title = getString(R.string.items_order)
        )
        btnNext.setButtonTitle(R.string.next)
    }

    private fun FragmentItemsOrderBinding.setupObservers() {
    }
}