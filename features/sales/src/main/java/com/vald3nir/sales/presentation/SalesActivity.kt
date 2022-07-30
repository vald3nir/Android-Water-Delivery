package com.vald3nir.sales.presentation

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vald3nir.commom.presentation.view.BaseActivity
import com.vald3nir.sales.R
import com.vald3nir.sales.databinding.ActivitySalesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SalesActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySalesBinding
    private val viewModel: SalesViewModel by viewModel()

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController =
            findNavController(R.id.nav_host_fragment_content_sales)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_my_orders, R.id.navigation_shopping
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_sales)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}