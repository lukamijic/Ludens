package com.ludens.androidApp.navigation.router

import java.util.*

interface CloseableRouterContext {
    val markedForClosing: LinkedList<String>
}
