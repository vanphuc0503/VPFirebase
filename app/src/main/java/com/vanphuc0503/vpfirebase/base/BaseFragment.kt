package com.vanphuc0503.vpfirebase.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel> : Fragment() {

    lateinit var binding: B
    lateinit var viewModel: V

    @get:LayoutRes
    abstract var layoutID: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (::binding.isInitialized.not()) {
            binding = DataBindingUtil.inflate(inflater, layoutID, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservable()
    }

    abstract fun initView()

    abstract fun initObservable()
}