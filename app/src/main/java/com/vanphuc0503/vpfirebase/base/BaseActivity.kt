package com.vanphuc0503.vpfirebase.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivity<B : ViewDataBinding, V : ViewModel> : AppCompatActivity() {
    lateinit var binding: B

    abstract val viewModel: ViewModel

    lateinit var controller: NavController

    @get:LayoutRes
    abstract val layoutId: Int

    @get:LayoutRes
    abstract val container: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (::binding.isInitialized.not()) {
            binding = DataBindingUtil.setContentView(this, layoutId)
        }
        initNavController()
        initView()
        initObservable()
    }

    abstract fun initView()

    abstract fun initObservable()

    private fun initNavController() {
        container?.let { container ->
            (supportFragmentManager.findFragmentById(container) as NavHostFragment).also {
                controller = it.navController
            }
        }
    }
}