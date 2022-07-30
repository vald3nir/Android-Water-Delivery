package com.vald3nir.sales.presentation.create

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.vald3nir.commom.presentation.view.BaseActivity
import com.vald3nir.sales.R
import com.vald3nir.sales.databinding.ActivityCreateOrderBinding
import com.vald3nir.sales.presentation.SalesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateOrderActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCreateOrderBinding
    private val viewModel: SalesViewModel by viewModel()

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController =
            findNavController(R.id.nav_host_fragment_content_sales)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_my_orders, R.id.navigation_shopping
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_sales)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}