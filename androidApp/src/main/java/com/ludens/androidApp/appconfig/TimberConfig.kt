package com.ludens.androidApp.appconfig

import android.util.Log
import com.ludens.androidApp.BuildConfig
import timber.log.Timber

/**
 * Timber app configuration
 */
class TimberAppConfig : AppConfig {
    override fun configure() {
        when (BuildConfig.BUILD_TYPE) {
            "debug" -> Timber.plant(DebugTree())
            "staging" -> Timber.plant(CrashlyticsTree(Log.INFO))
            "release" -> Timber.plant(CrashlyticsTree(Log.WARN))
            else -> throw IllegalStateException("Logging not set for ${BuildConfig.BUILD_TYPE} build type")
        }
    }
}

/**
 * Extends [Timber.DebugTree] but:
 * - Tag contains the thread name and FiveLog as a keyword for filtering
 * - Throws exception if priority == [Log.ERROR]
 */
private open class DebugTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        super.log(priority, tag, message, throwable)

        if (priority == Log.ERROR) {
            throw throwable ?: RuntimeException(message)
        }
    }

    override fun createStackElementTag(element: StackTraceElement) = "FiveLog Thread(${Thread.currentThread().name})"
}

/**
 * Logs everything above [minPriorityLevel] to Crashlytics
 */
private class CrashlyticsTree(private val minPriorityLevel: Int) : DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority < minPriorityLevel) {
            return
        }
    }
}
