package com.ludens.androidApp.core

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.ludens.androidApp.navigation.activity.BaseRoutingActivity

abstract class BaseBindingActivity<Binding : ViewBinding>(bindingInflater: (LayoutInflater) -> Binding) : BaseRoutingActivity() {

    protected val binding: Binding by lazy(LazyThreadSafetyMode.NONE) { bindingInflater(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialiseActivity()
        setContentView(binding.root)
        binding.initialiseView(savedInstanceState)
    }

    /** Override to do further initialisation of the Activity.
     *  This is called immediately after [super.onCreate(savedInstanceState)] */
    protected open fun initialiseActivity() {
        // Template
    }

    /** Override to initialise view */
    open fun Binding.initialiseView(savedInstanceState: Bundle?) {
        // Template
    }
}
