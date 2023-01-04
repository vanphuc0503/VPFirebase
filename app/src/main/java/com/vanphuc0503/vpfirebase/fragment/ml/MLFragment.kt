package com.vanphuc0503.vpfirebase.fragment.ml

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentMlBinding

class MLFragment : BaseFragment<FragmentMlBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_ml

    override fun initView() {

    }

    override fun initObservable() {

    }
}