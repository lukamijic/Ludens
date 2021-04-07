package com.ludens.shared.requirements

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSThread

actual fun logV(message: String) = println(logger.buildLog("V", message))
actual fun logD(message: String) = println(logger.buildLog("D", message))
actual fun logI(message: String) = println(logger.buildLog("I", message))
actual fun logW(message: String, throwable: Throwable?) {
    println(logger.buildLog("W", message))
    throwable?.let { println(logger.buildLog("W", it.stackTraceToString())) }
}

actual fun logE(message: String, throwable: Throwable?) {
    println(logger.buildLog("E", message))
    throwable?.let { println(logger.buildLog("E", it.stackTraceToString())) }
}

@SharedImmutable
private val logger = IosLogger()

class IosLogger {
    private val dateFormatter = NSDateFormatter().apply { dateFormat = "MM-dd HH:mm:ss.SSS" }

    private val logLevelToColor: HashMap<String, String> = hashMapOf(
        "V" to "💜V",
        "D" to "💚D",
        "I" to "💙I",
        "W" to "💛W",
        "E" to "❤️E",
        "A" to "💞A"
    )

    fun buildLog(priority: String, message: String): String =
        "${getCurrentTime()} ${logLevelToColor[priority]}/FiveLog ${if (NSThread.currentThread.isMainThread) "MainThread" else "BackgroundThread"} $message"

    private fun getCurrentTime() = dateFormatter.stringFromDate(NSDate())
}
