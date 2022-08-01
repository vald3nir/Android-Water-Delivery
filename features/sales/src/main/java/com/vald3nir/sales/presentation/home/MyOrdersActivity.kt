package com.vald3nir.sales.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.vald3nir.commom.presentation.view.BaseActivity
import com.vald3nir.sales.R
import com.vald3nir.sales.databinding.ActivityMyOrdersBinding
import com.vald3nir.sales.presentation.create.CreateOrderActivity
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
        binding.apply {
            setContentView(root)
            initView()
        }
    }

    private fun ActivityMyOrdersBinding.initView() {
        val navController = findNavController(R.id.nav_host_fragment_my_orders)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

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