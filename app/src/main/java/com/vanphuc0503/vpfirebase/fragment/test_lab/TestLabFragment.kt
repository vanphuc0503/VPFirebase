package com.vanphuc0503.vpfirebase.fragment.test_lab

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentTestLabBinding

class TestLabFragment :
    BaseFragment<FragmentTestLabBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_test_lab

    override fun initView() {

    }

    override fun initObservable() {

    }
}