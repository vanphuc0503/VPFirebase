package com.vanphuc0503.vpfirebase.fragment.analytics

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentAnalyticsBinding

class AnalyticsFragment : BaseFragment<FragmentAnalyticsBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_analytics

    override fun initView() {

    }

    override fun initObservable() {

    }
}