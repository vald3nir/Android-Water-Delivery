package com.vald3nir.diskwater.presentation.splash

import android.os.Bundle
import com.vald3nir.diskwater.common.core.BaseActivity
import com.vald3nir.diskwater.databinding.EmptyLayoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {

    private lateinit var binding: EmptyLayoutBinding
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmptyLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.view = this
    }

    override fun onPostResume() {
        super.onPostResume()
        viewModel.loadAppConfig()
    }
}