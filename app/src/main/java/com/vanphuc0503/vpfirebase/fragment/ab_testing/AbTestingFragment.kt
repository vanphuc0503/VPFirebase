package com.vanphuc0503.vpfirebase.fragment.ab_testing

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentAbTestingBinding

class AbTestingFragment : BaseFragment<FragmentAbTestingBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_ab_testing

    override fun initView() {

    }

    override fun initObservable() {

    }
}