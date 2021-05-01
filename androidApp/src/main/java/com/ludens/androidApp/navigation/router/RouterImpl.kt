package com.ludens.androidApp.navigation.router

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.ludens.androidApp.R
import com.ludens.androidApp.navigation.extensions.inTransaction
import com.ludens.androidApp.screen.dashboard.DashboardFragment
import com.ludens.androidApp.screen.main.MainActivity
import com.ludens.androidApp.screen.signup.SignUpActivity
import com.ludens.androidApp.screen.start.StartActivity

@IdRes
val MAIN_CONTAINER = R.id.main_container

class RouterImpl(
    private val activity: AppCompatActivity,
    fragmentManager: FragmentManager,
    closeableRouterContext: CloseableRouterContext
) : CloseableRouter(fragmentManager, closeableRouterContext) {

    override fun showStart() {
        activity.startActivity(StartActivity.createIntent(activity))
        activity.finishAffinity()
    }

    override fun showSignUp() {
        activity.startActivity(SignUpActivity.createIntent(activity))
        activity.finishAffinity()
    }

    override fun showDashboard() {
        if (activity !is MainActivity) {
            activity.startActivity(MainActivity.createIntent(activity))
            activity.finishAffinity()
        } else {
            fragmentManager.inTransaction {
                add(MAIN_CONTAINER, DashboardFragment.newInstance(), DashboardFragment.TAG)
            }
        }
    }

    override fun goBack() {
        if (fragmentManager.backStackEntryCount > 0) fragmentManager.popBackStackImmediate()
        else activity.finish()
    }
}
