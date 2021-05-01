package com.ludens.shared.screen.signup

import com.ludens.shared.core.BaseViewModel
import com.ludens.shared.navigation.Router
import com.ludens.shared.navigation.RoutingActionsDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.MutableStateFlow

class SignUpViewModel(
    routingActionsDispatcher: RoutingActionsDispatcher
) : BaseViewModel<SignUpViewState>(routingActionsDispatcher) {

    private val loadingPublisher = MutableStateFlow(false)

    init {
        query { loadingPublisher.map(::SignUpViewState) }
    }

    fun continueWithGoogle() {
        loadingPublisher.value = true

        runCommand {
            delay(3000)
            dispatchRoutingAction(Router::showDashboard)
        }
    }

    fun continueWithFacebook() {
        loadingPublisher.value = true

        runCommand {
            delay(3000)
            dispatchRoutingAction(Router::showDashboard)
        }
    }

    fun continueWithEmail() {
        loadingPublisher.value = true

        runCommand {
            delay(3000)
            dispatchRoutingAction(Router::showDashboard)
        }
    }

    fun showTermsOfUse() {
        /** TODO */
    }

    fun showPrivacyPolicy() {
        /** TODO */
    }
}
