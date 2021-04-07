package com.ludens.androidApp.navigation.router

import java.util.*

class CloseableRouterContextImpl : CloseableRouterContext {
    override val markedForClosing = LinkedList<String>()
}
