package com.vanphuc0503.vpfirebase.fragment.realtime_database

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentRealtimeDatabaseBinding

class RealtimeDatabaseFragment :
    BaseFragment<FragmentRealtimeDatabaseBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_realtime_database

    override fun initView() {

    }

    override fun initObservable() {

    }
}