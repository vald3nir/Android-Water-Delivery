package com.vald3nir.login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.vald3nir.commom.presentation.view.BaseFragment
import com.vald3nir.core_ui.extensions.actionDoneListener
import com.vald3nir.core_ui.extensions.afterTextChanged
import com.vald3nir.core_ui.extensions.hideKeyboard
import com.vald3nir.login.R
import com.vald3nir.login.databinding.FragmentLoginBinding
import com.vald3nir.core_utils.extensions.format
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            initViews()
            setupObservers()
        }
    }

    private fun FragmentLoginBinding.initViews() {
        setToolbarTitle(viewModel.titleScreen)
        btnRegister.isVisible = viewModel.showRegisterButton
        btnLogin.setup(
            title = R.string.login,
            clickListener = { login() }
        )
        viewModel.loadLoginData()
    }

    private fun FragmentLoginBinding.setupObservers() {

        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_login_to_navigation_register)
        }

        viewModel.loginDTO.observe(viewLifecycleOwner) {

            edtEmail.setText(it?.email)
            edtPassword.setText(it?.password)
            cbRememberLogin.isChecked = it?.rememberLogin == true

            edtEmail.afterTextChanged { loginDataChanged() }
            edtPassword.apply {
                afterTextChanged { loginDataChanged() }
                actionDoneListener { login() }
            }
        }

        viewModel.loginFormState.observe(viewLifecycleOwner) {
            val loginState = it
            edtEmailLayout.error = loginState.emailError
            edtPasswordLayout.error = loginState.passwordError
            btnLogin.showLoading(false)
        }
    }

    private fun FragmentLoginBinding.clearError() {
        edtEmailLayout.error = null
        edtPasswordLayout.error = null
    }

    private fun FragmentLoginBinding.loginDataChanged() {
        clearError()
        viewModel.checkLoginData(
            edtEmail.text.format(),
            edtPassword.text.format()
        )
    }

    private fun FragmentLoginBinding.login() {
        hideKeyboard()
        btnLogin.showLoading(true)
        viewModel.login(
            email = edtEmail.text.format(),
            password = edtPassword.text.format(),
            rememberLogin = cbRememberLogin.isChecked
        ) {
            btnLogin.showLoading(false)
            showMessage(it)
        }
    }
}