package com.ludens.shared.requirements

import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

actual val platformModule: Module = module {

    single<RoutingMediator> { RoutingMediatorImpl() }
}

actual inline fun <reified T : PlatformViewModel> Module.viewModelFactory(
    qualifier: Qualifier?,
    override: Boolean,
    noinline definition: Definition<T>
): BeanDefinition<T> = factory(qualifier, override, definition)