package com.vald3nir.sales.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.commom.presentation.view.BaseFragment
import com.vald3nir.sales.R
import com.vald3nir.sales.databinding.FragmentOrderSuccessBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderSuccessFragment : BaseFragment() {

    private val viewModel: SalesViewModel by viewModel()
    lateinit var binding: FragmentOrderSuccessBinding

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
    }

    private fun FragmentOrderSuccessBinding.initViews() {
        btnNext.apply {
            setup(
                title = R.string.finish,
                clickListener = {
                }
            )
        }
    }
}