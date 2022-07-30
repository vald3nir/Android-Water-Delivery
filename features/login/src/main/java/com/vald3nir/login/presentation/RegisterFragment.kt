package com.vald3nir.login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.commom.presentation.view.BaseFragment
import com.vald3nir.core_ui.extensions.actionDoneListener
import com.vald3nir.core_ui.extensions.afterTextChanged
import com.vald3nir.core_ui.extensions.hideKeyboard
import com.vald3nir.login.R
import com.vald3nir.login.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentRegisterBinding

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            initViews()
            setupObservers()
        }
    }

    private fun setToolbarTitle() {
        if (activity is LoginActivity) {
            (activity as LoginActivity).setToolbarTitle(R.string.register)
        }
    }

    private fun FragmentRegisterBinding.initViews() {
        setToolbarTitle()
        btnRegister.setup(
            title = R.string.register,
            clickListener = { registerNewUser() }
        )
    }

    private fun FragmentRegisterBinding.setupObservers() {
        edtEmail.afterTextChanged { registerDataChanged() }
        edtPassword.afterTextChanged { registerDataChanged() }
        edtConfirmPassword.apply {
            afterTextChanged { registerDataChanged() }
            actionDoneListener { registerNewUser() }
        }

        viewModel.registerFormState.observe(viewLifecycleOwner) {
            val loginState = it
            edtNameLayout.error = loginState.nameError
            edtEmailLayout.error = loginState.emailError
            edtPasswordLayout.error = loginState.passwordError
            edtConfirmPasswordLayout.error = loginState.confirmPasswordError
            btnRegister.showLoading(false)
        }
    }

    private fun FragmentRegisterBinding.clearError() {
        edtNameLayout.error = null
        edtEmailLayout.error = null
        edtPasswordLayout.error = null
        edtConfirmPasswordLayout.error = null
    }

    private fun FragmentRegisterBinding.registerDataChanged() {
        clearError()
        viewModel.checkRegisterData(
            name = edtName.text.toString(),
            email = edtEmail.text.toString(),
            password = edtPassword.text.toString(),
            confirmPassword = edtConfirmPassword.text.toString()
        )
    }

    private fun FragmentRegisterBinding.registerNewUser() {
        hideKeyboard()
        btnRegister.showLoading(true)
        viewModel.registerNewUser(
            name = edtName.text.toString(),
            email = edtEmail.text.toString(),
            password = edtPassword.text.toString(),
            confirmPassword = edtConfirmPassword.text.toString()
        ) {
            btnRegister.showLoading(false)
            showMessage(it)
        }
    }
}