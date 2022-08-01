package com.vald3nir.sales.presentation.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.commom.domain.dtos.AddressDTO
import com.vald3nir.commom.presentation.view.BaseFragment
import com.vald3nir.core_ui.extensions.afterTextChanged
import com.vald3nir.sales.R
import com.vald3nir.sales.databinding.FragmentConfirmAddressBinding
import com.vald3nir.sales.domain.form.AddressInputForm
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmAddressFragment : BaseFragment() {

    private val viewModel: AddressViewModel by viewModel()
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
    }

    private fun FragmentConfirmAddressBinding.initViews() {
        btnNext.setup(
            title = R.string.next,
            clickListener = {
                updateAddress()
                navigationToFragment(R.id.action_navigation_confirm_address_to_navigation_confirm_order)
            }
        )
    }

    private fun FragmentConfirmAddressBinding.fillAddress(addressFields: AddressDTO) {
        edtCep.setText(addressFields.cep)
        edtStreet.setText(addressFields.logradouro)
        edtNumber.setText(addressFields.number)
        edtComplement.setText(addressFields.complement)
        edtDistrict.setText(addressFields.bairro)
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
            },
            onError = {
                btnNext.showLoading(false)
            }
        )
    }
}