package com.ludens.shared.navigation.di

import com.ludens.shared.navigation.RoutingActionsDispatcher
import com.ludens.shared.navigation.RoutingActionsSource
import com.ludens.shared.navigation.RoutingMediator
import org.koin.dsl.module

val navigationModule = module {

    single<RoutingActionsDispatcher> { get<RoutingMediator>() }
    single<RoutingActionsSource> { get<RoutingMediator>() }
}
