package com.ludens.shared.screen.di

import com.ludens.shared.requirements.viewModelFactory
import com.ludens.shared.screen.signup.SignUpViewModel
import com.ludens.shared.screen.start.StartViewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModelFactory { StartViewModel(routingActionsDispatcher = get()) }

    viewModelFactory { SignUpViewModel(routingActionsDispatcher = get()) }
}
