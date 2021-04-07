package com.ludens.shared.requirements

import io.ktor.client.engine.*
import io.ktor.client.engine.android.*

actual fun platformEngine(): HttpClientEngine = Android.create()
