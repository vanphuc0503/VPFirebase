package com.vanphuc0503.vpfirebase.fragment.fire_remote_config

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentFireRemoteConfigBinding

class FireRemoteConfigFragment : BaseFragment<FragmentFireRemoteConfigBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_fire_remote_config

    override fun initView() {

    }

    override fun initObservable() {

    }
}