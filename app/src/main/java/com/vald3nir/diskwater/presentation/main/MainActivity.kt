package com.vald3nir.diskwater.presentation.main

import android.os.Bundle
import com.vald3nir.diskwater.common.core.BaseActivity
import com.vald3nir.diskwater.databinding.ActivityEmptyBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityEmptyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmptyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}