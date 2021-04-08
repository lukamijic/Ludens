package com.ludens.androidApp.screen.splash

import android.os.Bundle
import com.ludens.androidApp.core.BaseBindingActivity
import com.ludens.androidApp.databinding.ActivitySplashBinding

class SplashActivity : BaseBindingActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun ActivitySplashBinding.initialiseView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            logo.onFallEndAction = { router.showStart() }
            logo.fall()
        } else {
            router.showStart()
        }
    }
}
