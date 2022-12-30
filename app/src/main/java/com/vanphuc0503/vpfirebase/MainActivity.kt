package com.vanphuc0503.vpfirebase

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.vanphuc0503.vpfirebase.base.BaseActivity
import com.vanphuc0503.vpfirebase.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, ViewModel>() {

    override val layoutId: Int = R.layout.activity_main
    override val viewModel: ViewModel by viewModels()
    override val container: Int = R.id.container

    override fun initView() {
        controller.addOnDestinationChangedListener {controller, _, _ ->
            when(controller.currentDestination?.id) {
                 else -> {}
            }
        }
    }

    override fun initObservable() {

    }
}