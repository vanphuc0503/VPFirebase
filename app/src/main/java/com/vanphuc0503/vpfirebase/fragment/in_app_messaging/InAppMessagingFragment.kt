package com.vanphuc0503.vpfirebase.fragment.in_app_messaging

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentInAppMessagingBinding

class InAppMessagingFragment : BaseFragment<FragmentInAppMessagingBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_in_app_messaging

    override fun initView() {

    }

    override fun initObservable() {

    }
}