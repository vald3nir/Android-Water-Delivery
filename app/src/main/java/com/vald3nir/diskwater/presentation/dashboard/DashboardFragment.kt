package com.vald3nir.diskwater.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.data.dto.OrderDTO
import com.vald3nir.diskwater.databinding.FragmentDashboardBinding
import com.vald3nir.diskwater.databinding.OrderItemViewBinding
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.toolkit.core.componets.adapters.CustomListAdapterDiffer
import com.vald3nir.toolkit.utils.extensions.setupLayoutManager
import com.vald3nir.toolkit.utils.extensions.setupToolbar
import com.vald3nir.toolkit.utils.extensions.toMoney
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    private val viewModel: DashboardViewModel by viewModel()
    lateinit var binding: FragmentDashboardBinding

    private val mainCardAdapter by lazy {
        val adapter = CustomListAdapterDiffer(
            bindingInflater = OrderItemViewBinding::inflate,
            list = listOf(),
            itemDiffUtil = viewModel.orderDiffUtil(),
            ::bindAdapter
        )
        adapter
    }

    private fun bindAdapter(
        order: OrderDTO,
        itemViewBinding: OrderItemViewBinding,
        position: Int,
        any: Any
    ) {
        itemViewBinding.apply {
            txtTitle.text = order.clientName
            txtValue.text = order.total.toMoney()
            txtSubtitle.text = order.address
        }
    }

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.loadOrders()
    }

    private fun initViews() {
        binding.apply {
            toolbar.setupToolbar(
                activity = activity,
                title = "Minhas entregas",
                showBackButton = false,
            )
            clcOrdersOpen.apply {
                setTabs(viewModel.tabsList)
                getRecyclerView().apply {
                    adapter = mainCardAdapter
                    setupLayoutManager()
                }
            }
        }
    }

    private fun setupObservers() {

        binding.btnProducts.setOnClickListener {
            viewModel.replaceFragment(FragmentEnum.PRODUCTS)
        }

        mainCardAdapter.setOnItemClickListener(listener = { item, pos ->
            viewModel.replaceFragment(FragmentEnum.ORDER_DETAIL)
        })

        viewModel.ordersSelected.observe(viewLifecycleOwner) {
            mainCardAdapter.submitList(it)
            binding.clcOrdersOpen.notifyListSize(it.size)
        }
    }
}