package com.ludens.androidApp.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ludens.shared.core.BaseViewModel
import com.ludens.shared.core.Closeable
import kotlinx.coroutines.*

abstract class BaseFragment<ViewState : Any, Binding : ViewBinding>(private val bindingInflater: (LayoutInflater) -> Binding) : Fragment() {

    private val closeables = mutableListOf<Closeable>()

    abstract val model: BaseViewModel<ViewState>

    private var viewScope = CoroutineScope(Dispatchers.Main)

    private var _binding: Binding? = null

    protected val binding: Binding
        get() = _binding!!

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater(layoutInflater)
        return binding.root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.isClickable = true // To avoid clicks passing through

        viewScope.cancel()
        viewScope = MainScope()

        binding.initialiseView(savedInstanceState)

        model.viewStates().forEach { closeables.add(it.watch(this::render)) }
    }

    /** Override to initialise view */
    open fun Binding.initialiseView(savedInstanceState: Bundle?) {
        // Template
    }

    protected open fun render(viewState: ViewState) {
        // template
    }

    override fun onDestroyView() {
        _binding = null
        viewScope.cancel()

        closeables.forEach(Closeable::close)
        closeables.clear()
        super.onDestroyView()
    }
}
