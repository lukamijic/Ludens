package com.ludens.shared.requirements

import timber.log.Timber

actual fun logD(message: String) = Timber.d(message)

actual fun logE(message: String, throwable: Throwable?) = Timber.e(throwable, message)

actual fun logI(message: String) = Timber.i(message)

actual fun logV(message: String) = Timber.v(message)

actual fun logW(message: String, throwable: Throwable?) = Timber.w(throwable, message)
