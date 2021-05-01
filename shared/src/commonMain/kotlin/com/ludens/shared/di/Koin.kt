package com.ludens.shared.di

import com.ludens.shared.navigation.di.navigationModule
import com.ludens.shared.network.di.networkModule
import com.ludens.shared.requirements.platformModule
import com.ludens.shared.screen.di.viewModelModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(appModule: Module) : KoinApplication =
    startKoin {
        modules(
            appModule,
            navigationModule,
            platformModule,
            viewModelModule,
            networkModule
        )
    }
