package com.vanphuc0503.vpfirebase.fragment.google_admob

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentGoogleAdmobBinding

class GoogleAdmobFragment : BaseFragment<FragmentGoogleAdmobBinding, ViewModel>() {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_google_admob

    override fun initView() {

    }

    override fun initObservable() {

    }
}