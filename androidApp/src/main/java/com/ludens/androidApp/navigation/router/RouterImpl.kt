package com.ludens.androidApp.navigation.router

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class RouterImpl(
    private val activity: AppCompatActivity,
    fragmentManager: FragmentManager,
    closeableRouterContext: CloseableRouterContext
) : CloseableRouter(fragmentManager, closeableRouterContext) {

    override fun goBack() {
        if (fragmentManager.backStackEntryCount > 0) fragmentManager.popBackStackImmediate()
        else activity.finish()
    }
}
