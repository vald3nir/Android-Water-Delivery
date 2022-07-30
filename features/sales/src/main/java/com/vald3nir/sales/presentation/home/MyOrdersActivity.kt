package com.vald3nir.sales.presentation.home

import android.content.Intent
import android.os.Bundle
import com.vald3nir.commom.presentation.view.BaseActivity
import com.vald3nir.sales.databinding.ActivityMyOrdersBinding
import com.vald3nir.sales.presentation.SalesViewModel
import com.vald3nir.sales.presentation.create.CreateOrderActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyOrdersActivity : BaseActivity() {

    private lateinit var binding: ActivityMyOrdersBinding
    private val viewModel: SalesViewModel by viewModel()

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
        fabCreateOrder.setOnClickListener {
            startActivity(Intent(this@MyOrdersActivity, CreateOrderActivity::class.java))
        }
    }
}