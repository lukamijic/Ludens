package com.ludens.androidApp

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.ludens.androidApp.appconfig.AppConfig
import com.ludens.androidApp.appconfig.TimberAppConfig
import com.ludens.androidApp.feedback.haptic.HapticFeedback
import com.ludens.androidApp.feedback.haptic.HapticFeedbackImpl
import com.ludens.androidApp.navigation.router.CloseableRouterContext
import com.ludens.androidApp.navigation.router.CloseableRouterContextImpl
import com.ludens.androidApp.navigation.router.RouterImpl
import com.ludens.shared.di.initKoin
import com.ludens.shared.navigation.Router
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

class LudensApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(createPlatformModule(this))

        get<List<AppConfig>>().forEach(AppConfig::configure)
    }
}

private fun createPlatformModule(application: Application) = module {

    single { application }

    single<CloseableRouterContext> { CloseableRouterContextImpl() }

    single { listOf<AppConfig>(TimberAppConfig()) }

    factory<Router> { (activity: AppCompatActivity) ->
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        RouterImpl(activity = activity, fragmentManager = fragmentManager, closeableRouterContext = get())
    }

    single<Resources> { get<Application>().resources }

    single<HapticFeedback> { HapticFeedbackImpl(androidApplication().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator) }

}
