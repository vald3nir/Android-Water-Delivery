package com.vald3nir.diskwater.presentation.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.common.componets.CustomSheetDialog
import com.vald3nir.diskwater.common.core.BaseFragment
import com.vald3nir.diskwater.common.extensions.setup
import com.vald3nir.diskwater.common.extensions.setupToolbar
import com.vald3nir.diskwater.databinding.FragmentDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    private val viewModel: DashboardViewModel by viewModel()
    lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        viewModel.appView = appView
        binding.apply {
            toolbar.setupToolbar(
                activity = activity,
                title = "Minhas entregas",
                showBackButton = false,
                menuClickListener = { activity?.let { openMenu(it) } }
            )
            rcvOrders.setup(
                adapter =
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun openMenu(context: Context) {
        val dialog = CustomSheetDialog(context, viewModel.getMenuItems())
        dialog.setCancelable(true)
        dialog.show()
    }
}