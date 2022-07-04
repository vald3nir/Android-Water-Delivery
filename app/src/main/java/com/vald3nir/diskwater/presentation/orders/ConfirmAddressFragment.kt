package com.vald3nir.diskwater.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseFragment
import com.vald3nir.diskwater.data.dto.AddressDTO
import com.vald3nir.diskwater.data.form.AddressInputForm
import com.vald3nir.diskwater.databinding.FragmentConfirmAddressBinding
import com.vald3nir.diskwater.domain.navigation.FragmentEnum
import com.vald3nir.toolkit.utils.extensions.afterTextChanged
import com.vald3nir.toolkit.utils.extensions.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmAddressFragment : BaseFragment() {

    private val viewModel: OrderViewModel by viewModel()
    lateinit var binding: FragmentConfirmAddressBinding

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
        binding.setupObservers()
        viewModel.loadAddress()
    }

    private fun FragmentConfirmAddressBinding.setupObservers() {
        viewModel.addressFields.observe(viewLifecycleOwner) { addressFields ->
            fillAddress(addressFields)
        }
        viewModel.addressInputForm.observe(viewLifecycleOwner) { addressState ->
            fillErrorLayout(addressState)
        }
        edtStreet.afterTextChanged(afterTextChanged = { clearErrors() })
        edtDistrict.afterTextChanged(afterTextChanged = { clearErrors() })
        edtCep.afterTextChanged(afterTextChanged = {
            clearErrors()
            viewModel.searchByCep(cep = it)
        })
        btnNext.setButtonClickListener { updateAddress() }
    }

    private fun FragmentConfirmAddressBinding.initViews() {
        toolbar.setupToolbar(
            activity = activity,
            title = getString(R.string.confirm_address),
            showBackButton = true,
        )
        btnNext.setButtonTitle(R.string.next)
    }

    private fun FragmentConfirmAddressBinding.fillAddress(addressFields: AddressDTO) {
        edtCep.setText(addressFields.cep)
        edtStreet.setText(addressFields.street)
        edtNumber.setText(addressFields.number)
        edtComplement.setText(addressFields.complement)
        edtDistrict.setText(addressFields.district)
    }

    private fun FragmentConfirmAddressBinding.clearErrors() {
        edtCepLayout.error = null
        edtStreetLayout.error = null
        edtDistrictLayout.error = null
    }

    private fun FragmentConfirmAddressBinding.fillErrorLayout(addressState: AddressInputForm) {
        edtCepLayout.error = addressState.cepError
        edtStreetLayout.error = addressState.streetError
        edtDistrictLayout.error = addressState.districtError
    }

    private fun FragmentConfirmAddressBinding.updateAddress() {
        btnNext.showLoading(true)
        viewModel.confirmAddress(
            cep = edtCep.text.toString(),
            street = edtStreet.text.toString(),
            number = edtNumber.text.toString(),
            complement = edtComplement.text.toString(),
            district = edtDistrict.text.toString(),
            onSuccess = {
                btnNext.showLoading(false)
                viewModel.replaceFragment(FragmentEnum.ADD_ITEMS_ORDER)
            },
            onError = {
                btnNext.showLoading(false)
            }
        )
    }
}