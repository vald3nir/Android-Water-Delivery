package com.vald3nir.diskwater.presentation.dashboard

import android.os.Bundle
import com.vald3nir.diskwater.common.componets.CustomSheetDialog
import com.vald3nir.diskwater.common.core.BaseActivity
import com.vald3nir.diskwater.databinding.ActivityDashboardSalesmanBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardSalesmanActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardSalesmanBinding
    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardSalesmanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setupObservers()
    }

    private fun initViews() {
        viewModel.view = this
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