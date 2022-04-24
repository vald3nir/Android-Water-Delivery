package com.vald3nir.diskwater.presentation.dashboard

import android.os.Bundle
import androidx.lifecycle.Observer
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
        viewModel.view = this
        initViews()
        setupObservers()
    }

    private fun initViews() {
        binding.apply {}
    }


    private fun setupObservers() {
        viewModel.subscriberConsumptionRealTime()

        viewModel.consumptionRealTimeDTO.observe(this@DashboardActivity, Observer {
            val consumptionRealTimeDTO = it ?: return@Observer

        })
    }


    private fun openMenu() {
        val dialog = CustomSheetDialog(this, viewModel.getMenuItems())
        dialog.setCancelable(true)
        dialog.show()
    }
}