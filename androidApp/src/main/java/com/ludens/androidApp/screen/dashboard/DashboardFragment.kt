package com.ludens.androidApp.screen.dashboard

import android.os.Bundle
import com.ludens.androidApp.core.BaseFragment
import com.ludens.androidApp.databinding.FragmentDashboardBinding
import com.ludens.shared.screen.dashboard.DashboardViewModel
import com.ludens.shared.screen.dashboard.DashboardViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<DashboardViewState, FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    companion object {

        const val TAG = "DashboardFragment"

        fun newInstance() = DashboardFragment()
    }

    override val model: DashboardViewModel by viewModel()

    override fun FragmentDashboardBinding.initialiseView(savedInstanceState: Bundle?) {

    }

    override fun render(viewState: DashboardViewState) {

    }
}
