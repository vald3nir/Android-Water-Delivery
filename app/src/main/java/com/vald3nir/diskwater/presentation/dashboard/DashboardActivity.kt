package com.vald3nir.diskwater.presentation.dashboard

import android.os.Bundle
import com.vald3nir.diskwater.common.componets.CustomSheetDialog
import com.vald3nir.diskwater.common.core.BaseActivity
import com.vald3nir.diskwater.databinding.ActivityDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setupObservers()
    }

    private fun initViews() {
        viewModel.appView = this
        binding.apply {}
    }


    private fun setupObservers() {

    }


    private fun openMenu() {
        val dialog = CustomSheetDialog(this, viewModel.getMenuItems())
        dialog.setCancelable(true)
        dialog.show()
    }
}