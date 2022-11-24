package com.vald3nir.diskwater.presentation.features.sales.home

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.app.base.BaseActivity
import com.vald3nir.diskwater.databinding.ActivityMyOrdersBinding
import com.vald3nir.diskwater.presentation.features.sales.create.CreateOrderActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyOrdersActivity : BaseActivity() {

    private lateinit var binding: ActivityMyOrdersBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MyOrdersViewModel by viewModel()

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarComponent.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_my_orders)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.setupListeners()
    }

    private fun ActivityMyOrdersBinding.setupListeners() {
        fabCreateOrder.setOnClickListener {
            viewModel.clearCacheOrder()
            startActivity(Intent(this@MyOrdersActivity, CreateOrderActivity::class.java))
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_my_orders)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}