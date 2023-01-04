package com.vanphuc0503.vpfirebase.fragment.crashlytics

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.R
import com.vanphuc0503.vpfirebase.base.BaseFragment
import com.vanphuc0503.vpfirebase.databinding.FragmentCrashlyticsBinding
import com.vanphuc0503.vpfirebase.listener.CrashlyticsFragmentListener

class CrashlyticsFragment : BaseFragment<FragmentCrashlyticsBinding, ViewModel>(),
    CrashlyticsFragmentListener {
    override val viewModel: ViewModel by viewModels()
    override var layoutID: Int = R.layout.fragment_crashlytics

    override fun initView() {
        binding.listener = this
    }

    override fun initObservable() {

    }

    override fun clickCrashButton() {
        throw RuntimeException("Test Crash") // Force a crash
    }
}