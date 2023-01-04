package com.vanphuc0503.vpfirebase.fragment.performance_monitoring

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentPerformanceMonitoringBinding

class PerformanceMonitoringFragment :
    BaseFragment<FragmentPerformanceMonitoringBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_performance_monitoring

    override fun initView() {

    }

    override fun initObservable() {

    }
}