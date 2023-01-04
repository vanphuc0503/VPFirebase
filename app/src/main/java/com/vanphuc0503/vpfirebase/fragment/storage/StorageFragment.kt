package com.vanphuc0503.vpfirebase.fragment.storage

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentCrashlyticsBinding

class StorageFragment : BaseFragment<FragmentCrashlyticsBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_storage

    override fun initView() {

    }

    override fun initObservable() {

    }
}