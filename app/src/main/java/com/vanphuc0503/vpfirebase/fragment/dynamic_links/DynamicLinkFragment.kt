package com.vanphuc0503.vpfirebase.fragment.dynamic_links

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentDynamicLinkBinding

class DynamicLinkFragment : BaseFragment<FragmentDynamicLinkBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_dynamic_link

    override fun initView() {

    }

    override fun initObservable() {

    }
}