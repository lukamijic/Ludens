package com.ludens.shared.requirements

import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

expect val platformModule: Module

expect inline fun <reified T : PlatformViewModel> Module.viewModelFactory(
    qualifier: Qualifier? = null,
    override: Boolean = false,
    noinline definition: Definition<T>
): BeanDefinition<T>
