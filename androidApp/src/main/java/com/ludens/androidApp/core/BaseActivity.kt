package com.ludens.androidApp.core

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.ludens.shared.core.BaseViewModel
import com.ludens.shared.core.Closeable

abstract class BaseActivity<ViewState : Any, Binding : ViewBinding>(bindingInflater: (LayoutInflater) -> Binding) :
    BaseBindingActivity<Binding>(bindingInflater) {

    private val closeables = mutableListOf<Closeable>()

    abstract val model: BaseViewModel<ViewState>

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.viewStates().forEach { closeables.add(it.watch(this::render)) }
    }

    protected open fun render(viewState: ViewState) {
        // template
    }

    override fun onDestroy() {
        closeables.forEach(Closeable::close)
        closeables.clear()
        super.onDestroy()
    }
}
