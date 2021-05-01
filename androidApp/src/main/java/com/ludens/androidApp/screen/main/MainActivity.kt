package com.ludens.androidApp.screen.main

import android.content.Context
import android.content.Intent
import com.ludens.androidApp.core.BaseBindingActivity
import com.ludens.androidApp.databinding.ActivityMainBinding

class MainActivity : BaseBindingActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    companion object {
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
    }
}
