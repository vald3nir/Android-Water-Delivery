package com.vald3nir.diskwater.presentation.register

import android.os.Bundle
import androidx.lifecycle.Observer
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseActivity
import com.vald3nir.toolkit.extensions.actionDoneListener
import com.vald3nir.toolkit.extensions.afterTextChanged
import com.vald3nir.toolkit.extensions.hideKeyboard
import com.vald3nir.toolkit.extensions.setupToolbar
import com.vald3nir.diskwater.databinding.ActivityRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {

    private val viewModel: RegisterViewModel by viewModel()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setupObservers()
    }

    private fun initViews() {
        viewModel.appView = this
        binding.apply {
            toolbar.setupToolbar(
                activity = this@RegisterActivity,
                title = "${getString(R.string.register)} - ${getTypeAppName()}"
            )
            btnRegister.setOnClickListener { registerNewUser() }
            edtEmail.afterTextChanged { registerDataChanged() }
            edtPassword.apply { afterTextChanged { registerDataChanged() } }
            edtConfirmPassword.apply {
                afterTextChanged { registerDataChanged() }
                actionDoneListener { registerNewUser() }
            }
        }
        hideKeyboard()
    }

    private fun setupObservers() {
        viewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val loginState = it ?: return@Observer
            binding.edtEmailLayout.error = loginState.emailError
            binding.edtPasswordLayout.error = loginState.passwordError
            binding.edtConfirmPasswordLayout.error = loginState.confirmPasswordError
        })
    }

    private fun registerDataChanged() {
        binding.apply {
            viewModel.checkRegisterData(
                edtEmail.text.toString(),
                edtPassword.text.toString(),
                edtConfirmPassword.text.toString()
            )
        }
    }

    private fun registerNewUser() {
        binding.apply {
            viewModel.registerNewUser(
                email = edtEmail.text.toString(),
                password = edtPassword.text.toString(),
                confirmPassword = edtConfirmPassword.text.toString()
            )
        }
    }
}