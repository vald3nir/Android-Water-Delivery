package com.vald3nir.diskwater.presentation.address

import android.os.Bundle
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.core.BaseActivity
import com.vald3nir.diskwater.databinding.ActivityAddressBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressActivity : BaseActivity() {

    private val viewModel: AddressViewModel by viewModel()
    private lateinit var binding: ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setupObservers()
    }

    private fun initViews() {
        binding.apply {
            toolbar.title.text = getString(R.string.order_address)
        }
    }

    private fun setupObservers() {}

}