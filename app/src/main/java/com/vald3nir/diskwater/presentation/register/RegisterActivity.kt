package com.vald3nir.diskwater.presentation.register

import android.os.Bundle
import androidx.lifecycle.Observer
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseActivity
import com.vald3nir.diskwater.databinding.ActivityRegisterBinding
import com.vald3nir.toolkit.utils.extensions.actionDoneListener
import com.vald3nir.toolkit.utils.extensions.afterTextChanged
import com.vald3nir.toolkit.utils.extensions.hideKeyboard
import com.vald3nir.toolkit.utils.extensions.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {

    private val viewModel: RegisterViewModel by viewModel()
    private lateinit var binding: ActivityRegisterBinding

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.initViews()
        binding.setupObservers()
    }

    private fun ActivityRegisterBinding.initViews() {
        toolbar.setupToolbar(
            activity = this@RegisterActivity,
            title = "${getString(R.string.register)} - ${getTypeAppName()}"
        )
        btnRegister.setup(
            title = R.string.register,
            clickListener = { registerNewUser() }
        )
    }

    private fun ActivityRegisterBinding.setupObservers() {
        edtEmail.afterTextChanged { registerDataChanged() }
        edtPassword.afterTextChanged { registerDataChanged() }
        edtConfirmPassword.apply {
            afterTextChanged { registerDataChanged() }
            actionDoneListener { registerNewUser() }
        }

        viewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val loginState = it ?: return@Observer
            edtEmailLayout.error = loginState.emailError
            edtPasswordLayout.error = loginState.passwordError
            edtConfirmPasswordLayout.error = loginState.confirmPasswordError
            btnRegister.showLoading(false)
        })
    }

    private fun ActivityRegisterBinding.clearError() {
        edtEmailLayout.error = null
        edtPasswordLayout.error = null
        edtConfirmPasswordLayout.error = null
    }

    private fun ActivityRegisterBinding.registerDataChanged() {
        clearError()
        viewModel.checkRegisterData(
            edtEmail.text.toString(),
            edtPassword.text.toString(),
            edtConfirmPassword.text.toString()
        )
    }

    private fun ActivityRegisterBinding.registerNewUser() {
        hideKeyboard()
        btnRegister.showLoading(true)
        viewModel.registerNewUser(
            email = edtEmail.text.toString(),
            password = edtPassword.text.toString(),
            confirmPassword = edtConfirmPassword.text.toString()
        ) {
            btnRegister.showLoading(false)
            showMessage(it)
        }
    }
}