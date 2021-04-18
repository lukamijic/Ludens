package com.ludens.shared.screen.start

import com.ludens.shared.core.BaseViewModel
import com.ludens.shared.navigation.Router
import com.ludens.shared.navigation.RoutingActionsDispatcher

class StartViewModel(
    routingActionsDispatcher: RoutingActionsDispatcher,
) : BaseViewModel<StartViewState>(routingActionsDispatcher) {

    init {
        dispatchRoutingAction(Router::showSignUp)
    }
}
