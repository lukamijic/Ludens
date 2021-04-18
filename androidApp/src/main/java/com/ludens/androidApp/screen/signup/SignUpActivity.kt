package com.ludens.androidApp.screen.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.inSpans
import com.ludens.androidApp.R
import com.ludens.androidApp.core.BaseActivity
import com.ludens.androidApp.databinding.ActivitySignupBinding
import com.ludens.androidApp.ui.extensions.fadeIn
import com.ludens.androidApp.ui.extensions.fadeOut
import com.ludens.androidApp.ui.span.ClickSpan
import com.ludens.shared.screen.signup.SignUpViewModel
import com.ludens.shared.screen.signup.SignUpViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity<SignUpViewState, ActivitySignupBinding>(ActivitySignupBinding::inflate) {

    companion object {

        fun createIntent(context: Context) = Intent(context, SignUpActivity::class.java)
    }

    override val model: SignUpViewModel by viewModel()

    override fun ActivitySignupBinding.initialiseView(savedInstanceState: Bundle?) {
        setupLogin()
        setupTermsAndServices()

        continueWithGoogle.setOnClickListener { joystickLoader.fadeIn() }
        continueWithFacebook.setOnClickListener { joystickLoader.fadeIn() }
        continueWithEmail.setOnClickListener { joystickLoader.fadeIn() }

        joystickLoader.setOnClickListener { joystickLoader.fadeOut() }
    }

    private fun setupLogin() {
        binding.logIn.text = buildSpannedString {
            append(resources.getString(R.string.sign_up_already_have_an_account))
            append(" ")
            color(resources.getColor(R.color.signup_login_text_accent, null)) {
                append(resources.getString(R.string.sign_up_log_in))
            }
        }
    }

    private fun setupTermsAndServices() {
        binding.termsAndServices.text = buildSpannedString {
            appendLine(resources.getString(R.string.sign_up_by_signing_up_you_agree_to_ludens))
            inSpans(ClickSpan(model::showTermsOfUse)) {
                color(resources.getColor(R.color.signup_login_text_accent, null)) {
                    append(resources.getString(R.string.sign_up_terms_of_use))
                }
            }
            append(" ${resources.getString(R.string.sign_up_ampersand)} ")
            inSpans(ClickSpan(model::showTermsOfUse)) {
                color(resources.getColor(R.color.signup_login_text_accent, null)) {
                    append(resources.getString(R.string.sign_up_privacy_policy))
                }
            }
        }
    }
}
