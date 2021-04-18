package com.ludens.androidApp.navigation.router

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.ludens.androidApp.screen.signup.SignUpActivity
import com.ludens.androidApp.screen.start.StartActivity

class RouterImpl(
    private val activity: AppCompatActivity,
    fragmentManager: FragmentManager,
    closeableRouterContext: CloseableRouterContext
) : CloseableRouter(fragmentManager, closeableRouterContext) {

    override fun showStart() {
        activity.startActivity(StartActivity.createIntent(activity))
        activity.finish()
    }

    override fun showSignUp() {
        activity.startActivity(SignUpActivity.createIntent(activity))
        activity.finish()
    }

    override fun goBack() {
        if (fragmentManager.backStackEntryCount > 0) fragmentManager.popBackStackImmediate()
        else activity.finish()
    }
}
