package com.vanphuc0503.vpfirebase.fragment.app_check

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentAppCheckBinding

class AppCheckFragment : BaseFragment<FragmentAppCheckBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_app_check

    override fun initView() {

    }

    override fun initObservable() {

    }
}