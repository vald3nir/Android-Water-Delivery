package com.vald3nir.diskwater.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.databinding.FragmentOrderSuccesslBinding
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderSuccessFragment : BaseFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentOrderSuccesslBinding

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderSuccesslBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
    }

    private fun FragmentOrderSuccesslBinding.initViews() {
        btnNext.apply {
            setup(
                title = R.string.finish,
                clickListener = {
                    viewModel.replaceFragment(FragmentEnum.MY_ORDERS)
                }
            )
        }
    }
}