package com.ludens.shared.requirements

import io.ktor.client.engine.*
import io.ktor.client.engine.ios.*

actual fun platformEngine(): HttpClientEngine = Ios.create()
