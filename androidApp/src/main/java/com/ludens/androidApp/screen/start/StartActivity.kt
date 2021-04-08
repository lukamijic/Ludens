package com.ludens.androidApp.screen.start

import android.content.Context
import android.content.Intent
import com.ludens.androidApp.core.BaseActivity
import com.ludens.androidApp.databinding.ActivityStartBinding
import com.ludens.shared.screen.start.StartViewModel
import com.ludens.shared.screen.start.StartViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartActivity : BaseActivity<StartViewState, ActivityStartBinding>(ActivityStartBinding::inflate) {

    companion object {

        fun createIntent(context: Context) = Intent(context, StartActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    override val model: StartViewModel by viewModel()
}
